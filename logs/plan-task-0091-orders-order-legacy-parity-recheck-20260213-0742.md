# TASK-0091 — Orders + Order legacy parity recheck

Timestamp: 2026-02-13 07:42
Role: Senior Legacy Parity Analyst (Struts/JSP/Forms)

## Studied files
- `src/main/webapp/jsp/Orders.jsp`
- `src/main/webapp/jsp/Order.jsp`
- `src/main/webapp/jsp/OrderProduce.jsp`
- `src/main/webapp/ajax/OrderPaymentsGrid.jsp`
- `src/main/webapp/ajax/OrderPaySumsGrid.jsp`
- `src/main/webapp/ajax/OrderExecutedProducesGrid.jsp`
- `src/main/webapp/ajax/LinkedOrdersGrid.jsp`
- `src/main/webapp/dialogs/YesNo.jsp`
- `src/main/webapp/dialogs/ErrorDialog.jsp`
- `src/main/webapp/WEB-INF/classes/resources/struts/struts-config.xml`
- `src/main/webapp/WEB-INF/classes/resources/sql-resources.xml`
- `src/main/java/net/sam/dcl/action/OrdersAction.java`
- `src/main/java/net/sam/dcl/action/OrderAction.java`
- `src/main/java/net/sam/dcl/action/OrderProduceAction.java`
- `src/main/java/net/sam/dcl/form/OrdersForm.java`
- `src/main/java/net/sam/dcl/form/OrderForm.java`
- `src/main/java/net/sam/dcl/form/OrderProduceForm.java`
- `src/main/java/net/sam/dcl/dao/OrderDAO.java`

## Dispatches found
### OrdersAction
- `input`, `filter`, `internalFilter`, `restore`, `reload`, `block`
- page handlers via `grid` + `NEXT_PAGE|PREV_PAGE`

### OrderAction
- UI/open: `input`, `edit`, `clone`, `show`, `back`, `process`
- child flows: `newContractor`, `newContactPerson`, `newContractorFor`, `newProduce`, `cloneProduce`, `editProduce`, `deleteProduce`, `editExecuted`, `retFromProduceOperation`, `retFromContractor`, `retFromAttach`, `returnFromSelectCP`, `importExcel`, `produceMovement`, `fromProduceMovement`, `uploadTemplate`, `deferredAttach`, `deleteAttachment`, `downloadAttachment`, `changeViewNumber`
- print: `print`, `printLetter`
- ajax: `ajaxCheckSave`, `ajaxIsContractCopy`, `ajaxIsSpecificationCopy`, `ajaxOrderPaymentsGrid`, `ajaxAddToPaymentGrid`, `ajaxRemoveFromPaymentGrid`, `ajaxRecalculatePaymentGrid`, `ajaxOrderPaySumsGrid`, `ajaxAddToPaySumGrid`, `ajaxRemoveFromPaySumsGrid`, `ajaxRecalcPaySumGrid`

### OrderProduceAction
- `insert`, `edit`, `clone`, `process`, `forceProcess`, `selectProduce`, `returnFromSelectNomenclature`, `newProductTerm`, `deleteProductTerm`, `newReadyForShipping`, `deleteReadyForShipping`, `back`

## Added/changed parity rules in docs
- Orders docs rewritten to factual defaults, role gates, JS disable logic, dispatch contracts, grid PK/pagination/sort transport.
- Order edit docs rewritten with full section inventory, default state, role flags, create vs edit vs clone behavior.
- Contracts updated with exact legacy form field names and ajax dispatches.
- Behavior matrices normalized to explicit legacy flags and dispatch outcomes.
- Acceptance updated with strict presence parity and BLOCKED_FIELD criteria.
- Added/updated HAR blocked checklists for both screens (`orders` and `order_edit`).

## BLOCKED_FIELD list
- contractor add flow (`newContractor`)
- contact person add flow (`newContactPerson`)
- contractor_for add flow (`newContractorFor`)
- CP import flow (`selectCP`/`returnFromSelectCP`)
- Excel import flow (`importExcel`)
- produce dialog flow (`OrderProduceAction`)
- executed matrix flow (`OrderExecutedProducesAction`)
- produce movement flow (`ProduceMovementForOrderAction`)
