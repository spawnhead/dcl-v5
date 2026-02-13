# LEGACY_FLOW_CP_TO_ORDER_CLOSE

## A) Flow overview (facts only)
1. Пользователь открывает журнал КП через `/CommercialProposalsAction.do?dispatch=input` и работает с фильтрацией/пагинацией/блокировкой/клонированием в `CommercialProposalsAction` + `CommercialProposals.jsp`. Source: `src/main/webapp/WEB-INF/classes/resources/struts/struts-config.xml` (`/CommercialProposalsAction`), `src/main/java/net/sam/dcl/action/CommercialProposalsAction.java` (`input/filter/block/clone`), `src/main/webapp/jsp/CommercialProposals.jsp`.
2. Создание/редактирование КП идет через `/CommercialProposalAction.do?dispatch=input|edit|cloneLikeOldVersion|cloneLikeNewVersion` в `CommercialProposalAction`; сохранение через `dispatch=process` (`saveCommon`). Source: `struts-config.xml` (`/CommercialProposalAction`), `CommercialProposalAction.java` (`input/edit/cloneLike*/process/saveCommon`), `CommercialProposal.jsp`.
3. Из КП есть печатные ветки (`print`, `printInvoice`, `printContract`) и флаги состояния предложения (`cpr_proposal_received_flag`, `cpr_proposal_declined`) + блокировка (`cpr_block`). Source: `CommercialProposalAction.java` (`saveCommon`, `print*`, `show`), `CommercialProposalsAction.java` (`block`, `checkPrice`), `CommercialProposals.jsp`.
4. Конверсия КП в Contract реализована через выбор КП в селекторе `/SelectCPContractsAction.do?dispatch=input` и возврат в `/ContractAction.do?dispatch=importCP`; далее `Contract.importFromCP(...)` подготавливает Contract+Specification. Source: `struts-config.xml` (`/SelectCPContractsAction` forward `return`), `ContractAction.java` (`importCP`), `Contract.java` (`importFromCP`).
5. Конверсия КП в Order реализована через `/SelectCPOrderAction.do?dispatch=input` и возврат в `/OrderAction.do?dispatch=returnFromSelectCP`; `Order.importFromCP(...)` переносит позиции с фильтрацией заблокированной номенклатуры и категорий. Source: `struts-config.xml` (`/SelectCPOrderAction`), `OrderAction.java` (`selectCP`, `returnFromSelectCP`), `Order.java` (`importFromCP`).
6. Жизненный цикл Order (create/edit/save/payments/executed) идет через `/OrderAction.do` + `/OrderProduceAction.do` + `/OrderExecutedProducesAction.do`; сохранение (`process`) вызывает `saveCommon`, проверяет бизнес-условия, генерирует номер, сохраняет платежные сетки и пересчитывает блокировку заказа. Source: `struts-config.xml` (`/OrderAction`, `/OrderProduceAction`, `/OrderExecutedProducesAction`), `OrderAction.java` (`process/saveCommon`), `Order.jsp`, `OrderProduce.jsp`, `ajax/OrderPaymentsGrid.jsp`, `ajax/OrderPaySumsGrid.jsp`, `ajax/OrderExecutedProducesGrid.jsp`.
7. Закрытие/завершение заказа в найденном flow выражено не отдельным dispatch "close", а комбинацией `ord_executed_date`/`ord_annul` -> `ord_block=1` + `OrderDAO.saveBlock` (и DB procedure для фиктивного исполнения незакрытых строк при блокировке). Source: `OrderAction.java` (конец `saveCommon`), `OrderDAO.java` (`saveBlock`), `sql-resources.xml` (`process-order_produces_unexecuted`).

## B) State model (CP / Contract / Order)
CP state факты:
- `cpr_block` переключается из журнала (`CommercialProposalsAction.block`) и в check-price ветке (`checkPrice` принудительно ставит `cpr_block=1`). Source: `CommercialProposalsAction.java`, `CommercialProposalDAO.java`, `sql-resources.xml` (`commercial_proposal-update-block`, `commercial_proposal-update-checkPrice`).
- `cpr_proposal_received_flag` и `cpr_proposal_declined` сохраняются из формы КП; при clone оба флага сбрасываются в `0`. Source: `CommercialProposalAction.java` (`saveCurrentFormToBean`, `commonClone`).
- `cpr_date_accept` обязателен, если `cpr_proposal_received_flag=true`; также проверяется, что `cpr_date_accept >= cpr_date`. Source: `CommercialProposalAction.java` (`saveCommon`).

Contract state факты:
- Ветка `importCP` создает новый Contract-контекст и наполняет его через `Contract.importFromCP(...)`.
- `con_executed` на create-сценарии принудительно инициализируется `0` в `inputCommon`.
- Аннулирование ограничено методом `isCanAnnul()` (нельзя, если есть спецификация с `spc_executed` или `spc_occupied_in_pay_shp`). Source: `ContractAction.java` (`inputCommon/importCP`), `Contract.java` (`importFromCP`, `isCanAnnul`).

Order state факты:
- Ключевые флаги: `ord_executed_date`, `ord_annul`, `ord_block`, `ord_date_conf_all`, `ord_ready_for_deliv_date_all`.
- На сохранении `saveCommon`: если все количества исполнены и указана `ord_executed_date`, либо `ord_annul=1`, то `ord_block=1`; иначе `ord_block=null`; затем `OrderDAO.saveBlock(...)`.
- `OrderDAO.saveBlock` при блокировке вызывает `process-order_produces_unexecuted` (`dcl_process_opr_unexecuted`). Source: `OrderAction.java` (`saveCommon`), `OrderDAO.java` (`saveBlock`), `sql-resources.xml`.

## C) Screens involved by step
Шаг "CP list":
- URL: `/CommercialProposalsAction.do?dispatch=input|filter|block|checkPrice|clone`.
- Action class: `net.sam.dcl.action.CommercialProposalsAction`.
- Form bean: `CommercialProposals`.
- JSP forward: `.CommercialProposals` (`CommercialProposals.jsp`).
- Related ajax/dialogs: askUser dialogs `ask_block`, `ask_unblock`, `checkPriceAsk` в JSP. Source: `struts-config.xml`, `CommercialProposalsAction.java`, `CommercialProposals.jsp`.

Шаг "CP create/edit":
- URL: `/CommercialProposalAction.do?dispatch=input|edit|process|print|printInvoice|printContract|reload*|importExcel|uploadTemplate|deferredAttach`.
- Action class: `net.sam.dcl.action.CommercialProposalAction`.
- Form bean: `CommercialProposal`.
- JSP forward: `.CommercialProposal` (`CommercialProposal.jsp`).
- Related ajax/dialogs:
  - `ajaxProducesCommercialProposalGrid`, `ajaxProducesForAssembleMinskGrid`, `ajaxChangeCurrency`, `ajaxChangeNDSByString`, `ajaxChangeCourse`, `ajaxRecalcCommercialProposalGrid`, `ajaxGetTotal`, etc.
  - produce editor `/CommercialProposalProduceAction.do`.
  - import `/CommercialProposalImportAction.do?dispatch=input`.
  - attach flow via `DeferredAttachmentService` + `downloadAttachment`.
  Source: `struts-config.xml`, `CommercialProposalAction.java`, `CommercialProposal.jsp`, `CommercialProposalProduce.jsp`, `ajax/ProducesCommercialProposalGrid.jsp`.

Шаг "CP -> Contract import":
- URL: `/SelectCPContractsAction.do?dispatch=input` -> return `/ContractAction.do?dispatch=importCP`.
- Action classes: `SelectFromGridAction`, `net.sam.dcl.action.ContractAction`.
- Form bean: `Contract`.
- JSP forward after show: `.Contract`.
- Related screens: КП журнал как source selector (`input=/CommercialProposalsAction.do?dispatch=input`).
  Source: `struts-config.xml`, `ContractAction.java` (`importCP`).

Шаг "Contract edit/create":
- URL: `/ContractAction.do?dispatch=input|edit|process|retFromSpecificationOperation`.
- Action class: `net.sam.dcl.action.ContractAction`.
- Form bean: `Contract`.
- JSP forward: `.Contract`.
- Related ajax/dialogs: specification subflow via `/SpecificationAction.do`. Source: `struts-config.xml`, `ContractAction.java`.

Шаг "CP -> Order import":
- URL: `/SelectCPOrderAction.do?dispatch=input` -> return `/OrderAction.do?dispatch=returnFromSelectCP`.
- Action classes: `SelectFromGridAction`, `net.sam.dcl.action.OrderAction`.
- Form bean: `Order`.
- JSP forward: `.Order` (`Order.jsp`).
- Related behavior: если в КП есть заблокированные позиции, в Order ставится ошибка `error.order.haveBlockPosition`. Source: `struts-config.xml`, `OrderAction.java` (`returnFromSelectCP`), `Order.java` (`importFromCP`).

Шаг "Order create/edit/payments/executed":
- URL: `/OrderAction.do?dispatch=input|edit|clone|process|print|printLetter|ajax*|editExecuted|newProduce|editProduce|cloneProduce|deleteProduce`.
- Action class: `net.sam.dcl.action.OrderAction`.
- Form bean: `Order`.
- JSP forward: `.Order`.
- Related ajax/dialogs:
  - payments/pay sums fragments (`ajaxOrderPaymentsGrid`, `ajaxOrderPaySumsGrid`),
  - produce dialog `/OrderProduceAction.do`,
  - executed dialog `/OrderExecutedProducesAction.do?dispatch=edit`,
  - attachment deferred flow,
  - produce movement `/ProduceMovementForOrderAction.do`.
  Source: `struts-config.xml`, `OrderAction.java`, `Order.jsp`, `OrderProduceAction.java`, `ajax/OrderPaymentsGrid.jsp`, `ajax/OrderPaySumsGrid.jsp`, `ajax/OrderExecutedProducesGrid.jsp`.

## D) Data model touchpoints (CP↔Contract↔Order)
CP persistence touchpoints:
- Header table: `dcl_commercial_proposal` (PK `cpr_id`), insert via `dcl_commercial_proposal_insert`, update via `commercial_proposal-update`.
- Lines tables: `dcl_cpr_list_produce` (`lpc_id`, FK-like `cpr_id`) и `dcl_cpr_transport`.
- Numbering source: `get-num_commercial_proposal` -> `DCL_GET_NUM('DCL_COMMERCIAL_PROPOSAL')`.
Source: `sql-resources.xml`, `CommercialProposalDAO.java`.

Contract touchpoints:
- Header table: `dcl_contract` (PK `con_id`), insert `dcl_contract_insert`.
- `ContractAction.importCP` загружает `CommercialProposal` по `cpr_id` и вызывает `contract.importFromCP`, который создает первичную спецификацию в object-model контракта.
- Contract/spec close domain присутствует через `dcl_contract_closed_*` и `dcl_close_contract(:specification.spc_id)` (в рамках ContractClosed flow, не OrderAction).
Source: `ContractAction.java`, `Contract.java`, `sql-resources.xml`.

Order touchpoints:
- Header table: `dcl_order` (PK `ord_id`), insert via `dcl_order_insert`, update via `order-update`.
- Lines table: `dcl_ord_list_produce` (`opr_id`, FK-like `ord_id`).
- Payment tables: `dcl_ord_list_payment`, `dcl_ord_list_pay_sum` (используются DAO save/load).
- Executed lines: `dcl_ord_list_prod_executed` (`insert_order_produces_executed` / `select-order_produces_executed`).
- Numbering source: `get-num_order` -> `DCL_GET_NUM('DCL_ORDER')`.
Source: `sql-resources.xml`, `OrderDAO.java`, `OrderAction.java`.

Связки между сущностями (из кода):
- CP -> Contract: ключ передачи `cpr_id` через `SelectFromGridAction.getSelectedId` в `ContractAction.importCP`, затем field mapping в `Contract.importFromCP`.
- CP -> Order: ключ передачи `cpr_id` через `SelectFromGridAction.getSelectedId` в `OrderAction.returnFromSelectCP`, затем перенос позиций в `Order.importFromCP`.
- Order -> Contract/Specification связь в order form идет через `contract.con_id` и `specification.spc_id`; в SQL insert/update Order явно передается `specification.spc_id`.
Source: `ContractAction.java`, `OrderAction.java`, `Order.java`, `sql-resources.xml` (`order-insert`).

## E) Transitions & rules
Role/permission факты:
- КП журнал: admin/economist/manager/lawyer; КП редактирование: admin/economist/manager.
- Contract create: admin/economist/lawyer; Contract edit: admin/economist/manager/lawyer/user_in_lithuania/logistic.
- Order list: admin/economist/manager/user_in_lithuania/logistic; Order create: admin/economist/manager; Order edit: admin/economist/manager/user_in_lithuania/logistic.
Source: `xml-permissions.xml`.

Visibility/availability rules:
- CP list block toggle разрешен по `blockChecker`: admin всегда, economist/author только пока запись не блокирована.
- CP list edit/clone для onlyManager ограничен своим отделом (`dep_id`).
- Order list edit/clone для onlyManager ограничен своим отделом (`dep_id`), блок чекбокса только admin.
- Order edit read-only вычисляется в `show()` по `ord_block`, роли и section-specific флагам (`readOnlyIfNotLikeManager`, `readOnlyIfNotLikeLogist`, etc.).
Source: `CommercialProposalsAction.java`, `OrdersAction.java`, `OrderAction.java`.

Critical defaults/validation/number generation:
- CP номер генерируется в `saveCommon`: `BYM{YY}{MM}/{seq4}-{USR_CODE}` через `get-num_commercial_proposal`.
- Order номер генерируется в `saveCommon`: `{sellerPrefix}-{YY}{MM}/{seq4}-{USR_CODE}` через `get-num_order`.
- CP validate: final date >= date, accept-date обязателен для accepted, tender number обязателен при editable, проверка reserve/rest count в minsk-store режиме.
- Order validate: цепочка дат (sent->received->conf_sent/ready->executed), обязательность contractor_for+contract+specification при `ord_in_one_spec`, проверки executed counts, обязательные поля при `ord_ready_for_deliv_date_all` и `ord_date_conf_all`, DRP checks при `ord_all_include_in_spec`.
Source: `CommercialProposalAction.java` (`saveCommon`), `OrderAction.java` (`saveCommon`), `sql-resources.xml`.

Minsk-store specific rules (CP):
- При `cpr_assemble_minsk_store=1` в `show()` форсируются BYN/BYN, курс=1, DDP, date/final-date behavior, ряд полей ставится readonly; часть печатных/ценовых правил зависит от этого флага.
Source: `CommercialProposalAction.java` (`show`), `CommercialProposal.jsp`.

## F) Gaps / UNKNOWN
UNKNOWN: есть ли прямой автоматический переход Contract -> Order (не через ручное создание Order) в этом коде.
- Почему UNKNOWN: в изученных `ContractAction`, `OrderAction`, `struts-config.xml` нет явного forward/dispatch "createOrderFromContract".
- How to verify: проверить все action mapping по `OrderAction` и поиск `findForward("...Order...")` в `ContractAction`/`SpecificationAction`/смежных action.

UNKNOWN: считается ли "закрытие заказа" отдельным бизнес-статусом кроме `ord_block`/`ord_executed_date`/`ord_annul`.
- Почему UNKNOWN: в Order flow нет отдельного dispatch `close`; состояние выглядит вычисляемым через флаги и блокировку.
- How to verify: проверить DB процедуры/триггеры `dcl_order_*` и отчеты, где может быть отдельный close marker.

UNKNOWN: полный navigation-path (меню-клик) из `net.sam.dcl.navigation` до конкретных CP/Contract/Order URL.
- Почему UNKNOWN: в текущем срезе найден permission/struts mapping, но явный menu xml/path не локализован отдельным файлом меню.
- How to verify: найти источник генерации меню (search по title/URL) и связать role->menu item->action.
