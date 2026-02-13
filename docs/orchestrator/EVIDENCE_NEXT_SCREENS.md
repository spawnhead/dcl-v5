# EVIDENCE_NEXT_SCREENS.md

> Evidence pack for next screen queue selection. Generated: 2026-02-12T18:35 Minsk.
> No decisions, no prioritization — only facts.

---

## A) Modern status snapshot

### docs/screens/contract_attachments/
- SNAPSHOT.md: present
- CONTRACTS.md: present
- ACCEPTANCE.md: present
- BEHAVIOR_MATRIX.md: present
- TEST_DATA_SPEC.md: present
- QA_ROLE_PRESETS.md: present
- ACCEPTANCE status: UNKNOWN (no explicit PASS/FAIL marker)
- Last 2 logs: (none specific to this screen)
- BLOCKED: payloads/network.har.BLOCKED.md

### docs/screens/contract_create/
- SNAPSHOT.md: present
- CONTRACTS.md: present
- ACCEPTANCE.md: present
- BEHAVIOR_MATRIX.md: present
- TEST_DATA_SPEC.md: present
- QA_ROLE_PRESETS.md: present
- ACCEPTANCE status: PASS (per CONTINUITY.md TASK-0028)
- Last 2 logs:
  - logs/qa-n3a-n3-postgres-parity-20260212-1715.md
  - logs/debug-n3a-save-valid-20260212-1600.md
- BLOCKED: none (payloads complete)

### docs/screens/contract_import_cp/
- SNAPSHOT.md: present
- CONTRACTS.md: present
- ACCEPTANCE.md: present
- BEHAVIOR_MATRIX.md: present
- TEST_DATA_SPEC.md: present
- QA_ROLE_PRESETS.md: present
- ACCEPTANCE status: UNKNOWN (no implementation yet)
- Last 2 logs:
  - logs/plan-contract-import-cp-20260211-2100.md
- BLOCKED: payloads/network.har.BLOCKED.md

### docs/screens/contract_spec_create/
- SNAPSHOT.md: present
- CONTRACTS.md: present
- ACCEPTANCE.md: present
- BEHAVIOR_MATRIX.md: present
- TEST_DATA_SPEC.md: present
- QA_ROLE_PRESETS.md: present
- ACCEPTANCE status: PASS (API) per CONTINUITY.md TASK-0016
- Last 2 logs:
  - logs/qa-n3a1-n3a2-tabs-parity-20260212-1630.md
  - logs/dev-n3a1-n3a2-full-parity-20260212-1505.md
- BLOCKED: payloads/network.har.BLOCKED.md

### docs/screens/contractor_create/
- SNAPSHOT.md: present
- CONTRACTS.md: present
- ACCEPTANCE.md: present
- BEHAVIOR_MATRIX.md: present
- TEST_DATA_SPEC.md: present
- QA_ROLE_PRESETS.md: present
- ACCEPTANCE status: PASS (API) per CONTINUITY.md TASK-0016
- Last 2 logs:
  - logs/dev-task-0066-contact-persons-persist-20260212-1206.md
  - logs/dev-task-0067-contact-persons-modal-20260212-1000.md
- BLOCKED: payloads/network.har.BLOCKED.md

### docs/screens/contractor_edit/
- SNAPSHOT.md: present
- CONTRACTS.md: present
- ACCEPTANCE.md: present
- BEHAVIOR_MATRIX.md: present
- TEST_DATA_SPEC.md: present
- QA_ROLE_PRESETS.md: present
- ACCEPTANCE status: DONE (implementation) per TASK-0071
- Last 2 logs:
  - logs/dev-task-0072-contractor-edit-bank-and-contact-persons-20260212-1615.md
  - logs/plan-contractor-edit-20260212-1500.md
- BLOCKED: payloads/network.har.BLOCKED.md

### docs/screens/contractors/
- SNAPSHOT.md: present
- CONTRACTS.md: present
- ACCEPTANCE.md: present
- BEHAVIOR_MATRIX.md: present
- TEST_DATA_SPEC.md: present
- QA_ROLE_PRESETS.md: present
- ACCEPTANCE status: PASS per TASK-0044
- Last 2 logs:
  - logs/qa-contractors-list-20260211-2130.md
  - logs/dev-contractors-list-20260211-2120.md
- BLOCKED: payloads/network.har.BLOCKED.md

### docs/screens/contracts/
- SNAPSHOT.md: present
- CONTRACTS.md: present
- ACCEPTANCE.md: present
- BEHAVIOR_MATRIX.md: present
- TEST_DATA_SPEC.md: present
- QA_ROLE_PRESETS.md: present
- ACCEPTANCE status: PASS per TASK-0028
- Last 2 logs:
  - logs/qa-n3a-n3-postgres-parity-20260212-1715.md
  - logs/qa-contracts-20260211-0940.md
- BLOCKED: payloads/network.har.BLOCKED.md

### docs/screens/margin/
- SNAPSHOT.md: present
- CONTRACTS.md: present
- ACCEPTANCE.md: present
- BEHAVIOR_MATRIX.md: present
- TEST_DATA_SPEC.md: present
- QA_ROLE_PRESETS.md: present
- IMPLEMENTATION_NOTES.md: present
- QA_PARITY_REPORT.md: present
- ACCEPTANCE status: PARTIAL (fake data, 3 blockers fixed)
- Last 2 logs:
  - logs/dev-margin-parity-20260210-1200.log
  - logs/qa-margin-parity-final-20260210-1204.md
- BLOCKED: none (network.har present)

### docs/screens/orders/
- SNAPSHOT.md: present
- CONTRACTS.md: present
- ACCEPTANCE.md: present
- BEHAVIOR_MATRIX.md: present
- TEST_DATA_SPEC.md: present
- QA_ROLE_PRESETS.md: MISSING
- ACCEPTANCE status: PASS per logs/dev-orders-parity-fix-20260210-1532.log
- Last 2 logs:
  - logs/qa-orders-parity-20260210-1545.md
  - logs/dev-orders-parity-fix-20260210-1532.log
- BLOCKED: none

---

## B) Legacy screen universe (src/main)

### JSP entrypoints (src/main/webapp/jsp/*.jsp)
- Action.jsp, ActionRoles.jsp, Actions.jsp
- AdmZone.jsp, Assemble.jsp, AssemblePositions.jsp, Assembles.jsp
- Attachments.jsp
- Blank.jsp, Blanks.jsp
- CalculationState-grid.inc, CalculationState.jsp, CalculationStateDev.jsp, CalculationStateJustGrid.jsp
- ClosedRecord.jsp
- CommercialProposal.jsp, CommercialProposalProduce.jsp, CommercialProposals.jsp
- ConditionForContract.jsp, ConditionForContractProduce.jsp, ConditionsForContract.jsp
- ContactPerson.jsp, ContactPersonsList.jsp
- Contract.jsp, ContractClosed.jsp, Contracts.jsp, ContractsClosed.jsp
- ContractorRequest.jsp, ContractorRequests.jsp
- contractor.jsp, contractors.jsp, ContractorsForContractsClosedList.jsp, ContractorsList.jsp
- ContractsDepFromContractorList.jsp
- Countries.jsp, Country.jsp
- Currencies.jsp, currency.jsp, CurrencyRate.jsp, CurrencyRates.jsp
- CurrentWorks.jsp
- CustomCode.jsp, CustomCodeHistory.jsp, CustomCodes.jsp, CustomCodesList.jsp
- DeferredAttachments.jsp, DeferredUploadFile.jsp
- DeliveryRequest.jsp, DeliveryRequestPositions.jsp, DeliveryRequestProduce.jsp, DeliveryRequests.jsp
- department.jsp, departments.jsp
- DevZone.jsp
- EquipmentList.jsp
- error.jsp
- FilesPath.jsp, FilesPaths.jsp
- FixAttachments.jsp
- global-lock-error.jsp
- GoodsCirculation.jsp, GoodsRest.jsp, GoodsRestInMinsk.jsp, GoodsRestLithuania.jsp
- ImportFile.jsp
- IncoTermsList.jsp
- Instruction.jsp, Instructions.jsp, InstructionType.jsp, InstructionTypes.jsp
- invitation.jsp
- Journals.jsp
- Language.jsp, Languages.jsp
- LockedRecords.jsp, LockedRecords.old.jsp
- login-disabled.jsp, login.jsp
- Logs.jsp
- Margin.jsp, MarginDev.jsp
- MergeContractors.jsp
- MontageAdjustment.jsp, MontageAdjustmentHistory.jsp, MontageAdjustments.jsp, MontageAdjustmentsHistory.jsp
- MultipleFileUpload.jsp
- no_permission.jsp
- Nomenclature.jsp, NomenclatureProduce.jsp, NomenclatureProduceCustomCodeFromHistory.jsp, NomenclatureProduceCustomCodeHistory.jsp, NomenclatureProducesMerge.jsp
- Number1C.jsp, Number1CHistory.jsp
- Office.jsp
- Order.jsp, OrderExecutedProduces.jsp, OrderProduce.jsp, Orders.jsp, OrdersLogistics.jsp, OrdersStatistics.jsp, OrdersUnexecuted.jsp
- OutgoingLetter.jsp, OutgoingLetters.jsp
- Payment.jsp, Payments.jsp
- PaySum.jsp
- PersonalOffice.jsp
- ProduceCost.jsp, ProduceCostCustom.jsp, ProduceCostPositions.jsp, ProduceCostProduce.jsp, ProduceCostReport.jsp, ProducesCost.jsp, ProducesForAssembleMinsk.jsp
- PurchasePurpose.jsp, PurchasePurposes.jsp
- Purpose.jsp, Purposes.jsp
- RateNDS.jsp, RatesNDS.jsp
- References.jsp
- reloader.jsp
- Reports.jsp
- Reputation.jsp, Reputations.jsp
- Role.jsp, Roles.jsp
- Route.jsp, Routes.jsp
- Seller.jsp, Sellers.jsp, SellersList.jsp
- SerialNumberList.jsp
- Sessions.jsp
- Setting.jsp, Settings.jsp
- Shipping.jsp, ShippingDocType.jsp, ShippingDocTypes.jsp, ShippingPositions.jsp, ShippingReport.jsp, Shippings.jsp
- Specification.jsp, SpecificationImport.jsp, SpecificationImportPositions.jsp, SpecificationImports.jsp, SpecificationsDepFromContractList.jsp
- StuffCategories.jsp, StuffCategory.jsp
- Timeboard.jsp, Timeboards.jsp, TimeboardWork.jsp
- Unit.jsp, Units.jsp
- UploadFile.jsp
- user.jsp, UserRoles.jsp, users.jsp
- UserSetting.jsp, UserSettings.jsp
- WoodworkWorkFiles.jsp

Total JSP entrypoints: 160+

### AJAX JSPs (src/main/webapp/ajax/*.jsp)
- ClosedContractRecordsGrid.jsp
- ContractorRequestPrintGrid.jsp
- LinkedOrdersGrid.jsp
- MessagesGrid.jsp
- NomenclatureAddCategory.jsp
- NomenclatureCNGrid.jsp
- NomenclatureGrid.jsp
- NomenclatureProduceCNGrid.jsp
- NomenclatureProduceLTGrid.jsp
- NomenclatureTree.jsp
- OrderExecutedProducesGrid.jsp
- OrderPaymentsGrid.jsp
- OrderPaySumsGrid.jsp
- PaymentSumsGrid.jsp
- ProducesCommercialProposalGrid.jsp
- ProducesForAssembleMinskGrid.jsp
- ReservedInfoGrid.jsp
- ShippingManagersGrid.jsp
- ShippingsGrid.jsp
- SpecificationImportRecalcCostByPrice.jsp
- SpecificationImportRecalcPriceByCustomPercent.jsp
- SpecificationPaymentsGrid.jsp

Total AJAX JSPs: 22

### Dialogs (src/main/webapp/dialogs/*.jsp)
- ErrorDialog.jsp
- YesNo.jsp

Total dialogs: 2

---

## C) Struts action universe

### High-connectivity actions (by forwards count)

1. **ContractorAction** (path: /ContractorAction, /ContractorAddAction*, /ContractorEditAction*)
   - Form bean: contractor
   - Input: .contractor
   - Forwards: back, editReputation, addCountry, editPersonInContractor, addPersonInContractor
   - Action class: net.sam.dcl.action.ContractorAction
   - Context variants: 20+ (CommercialProposal, Contract, Order, Payment, Shipping, ContractorRequest, OutgoingLetter, CalculationState)

2. **ContractAction** (path: /ContractAction, /ContractActionCalculationState)
   - Form bean: Contract
   - Input: /ContractAction.do?dispatch=show
   - Forwards: form, back, newContractor, newSpecification, editSpecification, attach, backFromAttach
   - Action class: net.sam.dcl.action.ContractAction

3. **OrderAction** (path: /OrderAction)
   - Form bean: Order
   - Input: /OrderAction.do?dispatch=show
   - Forwards: form, back, newContractor, newContactPerson, newProduce, cloneProduce, editProduce, selectCP, importExcel, produceMovement, uploadTemplate, backFromAttach, editExecuted, ajaxOrderPaymentsGrid, ajaxOrderPaySumsGrid
   - Action class: net.sam.dcl.action.OrderAction

4. **CommercialProposalAction** (path: /CommercialProposalAction)
   - Form bean: CommercialProposal
   - Input: /CommercialProposalAction.do?dispatch=show
   - Forwards: form, back, newContractor, newContactPerson, newProduce, newProduceForAssembleMinsk, editProduce, editPurchasePurposes, importExcel, uploadTemplate, ajaxProducesForAssembleMinskGrid, ajaxProducesCommercialProposalGrid, backFromAttach
   - Action class: net.sam.dcl.action.CommercialProposalAction

5. **SpecificationAction** (path: /SpecificationAction, /SpecificationActionCalculationState)
   - Form bean: Specification
   - Input: .Specification
   - Forwards: back, backFromAttach, attach, backFromAttachList, ajaxSpecificationPaymentsGrid
   - Action class: net.sam.dcl.action.SpecificationAction

6. **PaymentAction** (path: /PaymentAction, /PaymentActionCalculationState)
   - Form bean: Payment
   - Input: /PaymentAction.do?dispatch=show
   - Forwards: form, back, backToReport, newContractor, newPaySum, editPaySum, ajaxPaymentSumsGrid
   - Action class: net.sam.dcl.action.PaymentAction

7. **ShippingAction** (path: /ShippingAction, /ShippingActionCalculationState)
   - Form bean: Shipping
   - Input: /ShippingAction.do?dispatch=show
   - Forwards: form, back, editShippingPositions, newContractor, ajaxShippingsGrid, ajaxManagersGrid
   - Action class: net.sam.dcl.action.ShippingAction

8. **ConditionForContractAction** (path: /ConditionForContractAction)
   - Form bean: ConditionForContract
   - Input: /ConditionForContractAction.do?dispatch=show
   - Forwards: form, back, newContractor, editContractor, newContactPerson, editContactPerson, editPurchasePurposes, newProduce, cloneProduce, editProduce, selectCP, importExcel, uploadTemplate, backFromAttach
   - Action class: net.sam.dcl.action.ConditionForContractAction

9. **ContractorRequestAction** (path: /ContractorRequestAction)
   - Form bean: ContractorRequest
   - Input: /ContractorRequestAction.do?dispatch=show
   - Forwards: form, back, newContractor, newContactPerson, selectProduce, backFromAttach, selectOrder, ajaxContractorRequestPrintGrid, ajaxLinkedOrdersGrid
   - Action class: net.sam.dcl.action.ContractorRequestAction

10. **NomenclatureAction** (path: /NomenclatureAction)
    - Form bean: Nomenclature
    - Input: .Nomenclature
    - Forwards: ajaxTree, ajaxGrid, ajaxCNGrid, ajaxAddCategory, createNomenclatureProduce, mergeProduces, importProduces, uploadTemplate
    - Action class: net.sam.dcl.controller.actions.HibernateAction

### List actions (grid screens)
- /ContractsAction -> Contracts.jsp (form: Contracts)
- /OrdersAction -> Orders.jsp (form: Orders)
- /ContractorsAction -> contractors.jsp (form: contractors)
- /CommercialProposalsAction -> CommercialProposals.jsp (form: CommercialProposals)
- /PaymentsAction -> Payments.jsp (form: Payments)
- /ShippingsAction -> Shippings.jsp (form: Shippings)
- /AssemblesAction -> Assembles.jsp (form: Assembles)
- /DeliveryRequestsAction -> DeliveryRequests.jsp (form: DeliveryRequests)
- /SpecificationImportsAction -> SpecificationImports.jsp (form: SpecificationImports)
- /ProducesCostAction -> ProducesCost.jsp (form: ProducesCost)
- /ContractsClosedAction -> ContractsClosed.jsp (form: ContractsClosed)
- /ContractorRequestsAction -> ContractorRequests.jsp (form: ContractorRequests)
- /TimeboardsAction -> Timeboards.jsp (form: Timeboards)
- /OutgoingLettersAction -> OutgoingLetters.jsp (form: OutgoingLetters)
- /InstructionsAction -> Instructions.jsp (form: Instructions)

### Report actions
- /MarginAction -> Margin.jsp (form: Margin)
- /GoodsRestAction -> GoodsRest.jsp (form: GoodsRest)
- /CalculationStateAction -> CalculationState.jsp (form: CalculationState)
- /OrdersStatisticsAction -> OrdersStatistics.jsp (form: OrdersStatistics)
- /OrdersUnexecutedAction -> OrdersUnexecuted.jsp (form: OrdersUnexecuted)
- /OrdersLogisticsAction -> OrdersLogistics.jsp (form: OrdersLogistics)
- /ShippingReportAction -> ShippingReport.jsp (form: ShippingReport)
- /GoodsCirculationAction -> GoodsCirculation.jsp (form: GoodsCirculation)
- /ProduceCostReportAction -> ProduceCostReport.jsp (form: ProduceCostReport)

### Reference data actions
- /CountriesAction -> Countries.jsp (form: Countries)
- /CurrenciesAction -> Currencies.jsp (form: Currencies)
- /CurrencyRatesAction -> CurrencyRates.jsp (form: CurrencyRates)
- /StuffCategoriesAction -> StuffCategories.jsp (form: StuffCategories)
- /RoutesAction -> Routes.jsp (form: Routes)
- /SellersAction -> Sellers.jsp (form: Sellers)
- /UnitsAction -> Units.jsp (form: Units)
- /PurposesAction -> Purposes.jsp (form: Purposes)
- /PurchasePurposesAction* -> PurchasePurposes.jsp (form: PurchasePurposes)
- /ShippingDocTypesAction -> ShippingDocTypes.jsp (form: ShippingDocTypes)
- /RatesNDSAction -> RatesNDS.jsp (form: RatesNDS)
- /ReputationsAction* -> Reputations.jsp (form: Reputations)
- /InstructionTypesAction* -> InstructionTypes.jsp (form: InstructionTypes)
- /FilesPathsAction -> FilesPaths.jsp (form: FilesPaths)
- /LanguagesAction -> Languages.jsp (form: Languages)
- /CustomCodesAction -> CustomCodes.jsp (form: CustomCodes)
- /MontageAdjustmentsAction -> MontageAdjustments.jsp (form: MontageAdjustments)
- /DepartmentsAction -> departments.jsp (form: departments)

### Admin actions
- /UsersAction -> users.jsp (form: users)
- /RolesAction -> Roles.jsp (form: Roles)
- /ActionsAction -> Actions.jsp (form: Actions)
- /SettingsAction -> Settings.jsp (form: Settings)
- /BlanksAction -> Blanks.jsp (form: Blanks)

---

## D) Candidate flows evidence (top 10 by connectivity)

### 1. Contractor (create/edit from multiple contexts)
- Legacy JSP: contractor.jsp
- Action paths: /ContractorAction, /ContractorAddAction*, /ContractorEditAction* (20+ context variants)
- Form bean: contractor
- Forwards: back (context-dependent), editReputation, addCountry, editPersonInContractor, addPersonInContractor
- Related AJAX/dialogs: ContactPerson.jsp, Reputations.jsp, Country.jsp
- Related DAO/Service: ContractorAction, ContractorDAO (by naming convention)
- Modern spec pack: docs/screens/contractor_create/, docs/screens/contractor_edit/
- Modern implementation: DONE (TASK-0071, TASK-0072)
- Status: IMPLEMENTED

### 2. Contract (create from list, import from CP)
- Legacy JSP: Contract.jsp, Contracts.jsp
- Action paths: /ContractAction, /ContractsAction, /SelectCPContractsAction
- Form bean: Contract, Contracts
- Forwards: form, back, newContractor, newSpecification, editSpecification, attach, selectCP
- Related AJAX/dialogs: Specification.jsp, DeferredAttachments.jsp
- Related DAO/Service: ContractAction, ContractDAO
- Modern spec pack: docs/screens/contracts/, docs/screens/contract_create/, docs/screens/contract_import_cp/
- Modern implementation: PARTIAL (list + create done, import_cp pending)
- Status: PARTIAL

### 3. Order (create/edit with produces)
- Legacy JSP: Order.jsp, Orders.jsp, OrderProduce.jsp
- Action paths: /OrderAction, /OrdersAction, /OrderProduceAction
- Form bean: Order, Orders, OrderProduce
- Forwards: form, back, newContractor, newContactPerson, newProduce, cloneProduce, editProduce, selectCP, importExcel, editExecuted
- Related AJAX/dialogs: OrderPaymentsGrid.jsp, OrderPaySumsGrid.jsp, OrderExecutedProducesGrid.jsp
- Related DAO/Service: OrderAction, OrderDAO
- Modern spec pack: docs/screens/orders/
- Modern implementation: DONE (list only)
- Status: LIST DONE, CREATE/EDIT PENDING

### 4. Commercial Proposal (create/edit with produces)
- Legacy JSP: CommercialProposal.jsp, CommercialProposals.jsp, CommercialProposalProduce.jsp
- Action paths: /CommercialProposalAction, /CommercialProposalsAction, /CommercialProposalProduceAction
- Form bean: CommercialProposal, CommercialProposals, CommercialProposalProduce
- Forwards: form, back, newContractor, newContactPerson, newProduce, editProduce, importExcel, uploadTemplate
- Related AJAX/dialogs: ProducesCommercialProposalGrid.jsp, ProducesForAssembleMinskGrid.jsp, ReservedInfoGrid.jsp
- Related DAO/Service: CommercialProposalAction, CommercialProposalDAO
- Modern spec pack: NONE
- Modern implementation: NONE
- Status: NOT STARTED

### 5. Specification (create/edit from Contract)
- Legacy JSP: Specification.jsp
- Action paths: /SpecificationAction
- Form bean: Specification
- Forwards: back, backFromAttach, attach, ajaxSpecificationPaymentsGrid
- Related AJAX/dialogs: SpecificationPaymentsGrid.jsp, DeferredAttachments.jsp
- Related DAO/Service: SpecificationAction, SpecificationDAO
- Modern spec pack: docs/screens/contract_spec_create/
- Modern implementation: DONE (draft spec for contract create)
- Status: PARTIAL (draft only, full edit pending)

### 6. Payment (create/edit with PaySums)
- Legacy JSP: Payment.jsp, Payments.jsp, PaySum.jsp
- Action paths: /PaymentAction, /PaymentsAction, /PaySumAction
- Form bean: Payment, Payments, PaySum
- Forwards: form, back, backToReport, newContractor, newPaySum, editPaySum, ajaxPaymentSumsGrid
- Related AJAX/dialogs: PaymentSumsGrid.jsp
- Related DAO/Service: PaymentAction, PaymentDAO
- Modern spec pack: NONE
- Modern implementation: NONE
- Status: NOT STARTED

### 7. Shipping (create/edit with positions)
- Legacy JSP: Shipping.jsp, Shippings.jsp, ShippingPositions.jsp
- Action paths: /ShippingAction, /ShippingsAction, /ShippingPositionsAction
- Form bean: Shipping, Shippings, ShippingPositions
- Forwards: form, back, editShippingPositions, newContractor, ajaxShippingsGrid, ajaxManagersGrid
- Related AJAX/dialogs: ShippingsGrid.jsp, ShippingManagersGrid.jsp
- Related DAO/Service: ShippingAction, ShippingDAO
- Modern spec pack: NONE
- Modern implementation: NONE
- Status: NOT STARTED

### 8. Condition for Contract (create/edit with produces)
- Legacy JSP: ConditionForContract.jsp, ConditionsForContract.jsp, ConditionForContractProduce.jsp
- Action paths: /ConditionForContractAction, /ConditionsForContractAction, /ConditionForContractProduceAction
- Form bean: ConditionForContract, ConditionsForContract, ConditionForContractProduce
- Forwards: form, back, newContractor, editContractor, newContactPerson, editContactPerson, newProduce, cloneProduce, editProduce, selectCP, importExcel
- Related AJAX/dialogs: (none specific)
- Related DAO/Service: ConditionForContractAction
- Modern spec pack: NONE
- Modern implementation: NONE
- Status: NOT STARTED

### 9. Contractor Request (create/edit with orders link)
- Legacy JSP: ContractorRequest.jsp, ContractorRequests.jsp
- Action paths: /ContractorRequestAction, /ContractorRequestsAction
- Form bean: ContractorRequest, ContractorRequests
- Forwards: form, back, newContractor, newContactPerson, selectProduce, selectOrder, ajaxContractorRequestPrintGrid, ajaxLinkedOrdersGrid
- Related AJAX/dialogs: ContractorRequestPrintGrid.jsp, LinkedOrdersGrid.jsp
- Related DAO/Service: ContractorRequestAction
- Modern spec pack: NONE
- Modern implementation: NONE
- Status: NOT STARTED

### 10. Nomenclature (tree + grid + produce)
- Legacy JSP: Nomenclature.jsp, NomenclatureProduce.jsp
- Action paths: /NomenclatureAction, /NomenclatureProduceAction
- Form bean: Nomenclature, NomenclatureProduce
- Forwards: ajaxTree, ajaxGrid, ajaxCNGrid, ajaxAddCategory, createNomenclatureProduce, mergeProduces, importProduces
- Related AJAX/dialogs: NomenclatureTree.jsp, NomenclatureGrid.jsp, NomenclatureCNGrid.jsp, NomenclatureProduceCNGrid.jsp, NomenclatureProduceLTGrid.jsp
- Related DAO/Service: NomenclatureAction (HibernateAction)
- Modern spec pack: NONE
- Modern implementation: NONE
- Status: NOT STARTED

---

## E) Blockers evidence

### Screens with BLOCKED.md requiring HAR capture

1. **contract_attachments/payloads/network.har.BLOCKED.md**
   - Blocker: HAR capture for deferred attachments flow (session-based, con_id=null)
   - Required: Capture upload/delete/download network requests in legacy

2. **contract_import_cp/payloads/network.har.BLOCKED.md**
   - Blocker: HAR capture for CP selection grid + import flow
   - Required: Capture selectCP dispatch, grid load, row select, return to contract

3. **contract_spec_create/payloads/network.har.BLOCKED.md**
   - Blocker: HAR capture for specification create AJAX payloads
   - Required: Capture payments/date/reminder/prices AJAX + multipart attachments

4. **contractor_create/payloads/network.har.BLOCKED.md**
   - Blocker: HAR capture for nested grid fields (accounts, users, contact persons)
   - Required: Capture multipart/x-www-form-urlencoded wire format for grid edits

5. **contractor_edit/payloads/network.har.BLOCKED.md**
   - Blocker: HAR capture for edit/process + nested grid edits
   - Required: Capture exact POST param names for nested grids

6. **contractors/payloads/network.har.BLOCKED.md**
   - Blocker: HAR capture for contractors list delete request
   - Required: Capture delete dispatch, confirmation dialog, response

7. **contracts/payloads/network.har.BLOCKED.md**
   - Blocker: HAR capture for contracts list filter/grid
   - Required: Capture filter dispatch, grid load, pagination

### Common blocker theme
All blockers require access to running legacy application for HAR capture. Without legacy runtime, exact wire-format for nested grids and specific dispatch payloads cannot be confirmed.

### Resolution paths
1. Deploy legacy application locally (Firebird + Tomcat)
2. Use Chrome DevTools to capture HAR files
3. Replace BLOCKED.md with network.har
4. Update SNAPSHOT/CONTRACTS to remove UNCONFIRMED markers

---

## F) Log files summary (most recent by screen)

### contract_create
- logs/qa-n3a-n3-postgres-parity-20260212-1715.md
- logs/debug-n3a-save-valid-20260212-1600.md

### contractor_create
- logs/dev-task-0066-contact-persons-persist-20260212-1206.md
- logs/dev-task-0067-contact-persons-modal-20260212-1000.md

### contractor_edit
- logs/dev-task-0072-contractor-edit-bank-and-contact-persons-20260212-1615.md
- logs/plan-contractor-edit-20260212-1500.md

### contractors
- logs/qa-contractors-list-20260211-2130.md
- logs/dev-contractors-list-20260211-2120.md

### contracts
- logs/qa-n3a-n3-postgres-parity-20260212-1715.md
- logs/qa-contracts-20260211-0940.md

### margin
- logs/dev-margin-parity-20260210-1200.log
- logs/qa-margin-parity-final-20260210-1204.md

### orders
- logs/qa-orders-parity-20260210-1545.md
- logs/dev-orders-parity-fix-20260210-1532.log

---

End of evidence pack.
