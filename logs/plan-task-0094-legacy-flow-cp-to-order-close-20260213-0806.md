# TASK-0094 — legacy flow CP -> Contract -> Order -> close trace

Timestamp: 2026-02-13 08:06

## Commands executed
- rg --files | rg 'AGENTS.md|struts-config.xml|docs/doc_old|docs/Doc_project|navigation|CommercialProposal|OrderAction|Contract.*Action'
- rg -n "CommercialProposal|cpr_|CP|clone|block|minsk_store" src/main
- rg -n "OrderAction|OrdersAction|ord_|ready_for_deliv|executed|close" src/main/java src/main/webapp/WEB-INF/classes/resources/struts/struts-config.xml src/main/webapp/jsp/Order.jsp src/main/webapp/jsp/Orders.jsp
- rg -n "Contract.*Action|con_|selectCP|Import|CommercialProposal|cpr_id|returnFromSelectCP|newContract|clone|block" src/main/java/net/sam/dcl/action src/main/webapp/jsp src/main/webapp/WEB-INF/classes/resources/struts/struts-config.xml
- rg -n 'public ActionForward ...' / targeted rg in:
  - CommercialProposalsAction.java
  - CommercialProposalAction.java
  - ContractAction.java
  - OrderAction.java
  - OrderProduceAction.java
- rg -n 'importFromCP|cpr_id|con_id|ord_id|...'
  - src/main/java/net/sam/dcl/beans/{Contract,Order,CommercialProposal}.java
  - src/main/java/net/sam/dcl/dao/{CommercialProposalDAO,OrderDAO}.java
  - src/main/webapp/WEB-INF/classes/resources/sql-resources.xml
- rg -n permission gates in src/main/webapp/WEB-INF/classes/resources/xml-permissions.xml
- rg -n dispatches in jsp:
  - src/main/webapp/jsp/CommercialProposals.jsp
  - src/main/webapp/jsp/CommercialProposal.jsp
  - src/main/webapp/jsp/Order.jsp

## Key files used for transition evidence
- Struts mappings:
  - src/main/webapp/WEB-INF/classes/resources/struts/struts-config.xml
- CP list/edit:
  - src/main/java/net/sam/dcl/action/CommercialProposalsAction.java
  - src/main/java/net/sam/dcl/action/CommercialProposalAction.java
  - src/main/webapp/jsp/CommercialProposals.jsp
  - src/main/webapp/jsp/CommercialProposal.jsp
- CP -> Contract:
  - src/main/java/net/sam/dcl/action/ContractAction.java (importCP)
  - src/main/java/net/sam/dcl/beans/Contract.java (importFromCP)
- CP -> Order + Order closure:
  - src/main/java/net/sam/dcl/action/OrderAction.java (returnFromSelectCP, saveCommon)
  - src/main/java/net/sam/dcl/beans/Order.java (importFromCP)
  - src/main/java/net/sam/dcl/dao/OrderDAO.java (saveBlock)
  - src/main/webapp/WEB-INF/classes/resources/sql-resources.xml (process-order_produces_unexecuted, numbering entries)
- Role gates:
  - src/main/webapp/WEB-INF/classes/resources/xml-permissions.xml

## Where transitions were found
- CP selector -> Contract import: SelectCPContractsAction return to ContractAction.importCP (struts-config + ContractAction).
- CP selector -> Order import: SelectCPOrderAction return to OrderAction.returnFromSelectCP (struts-config + OrderAction).
- Order close/block outcome: OrderAction.saveCommon sets ord_block then OrderDAO.saveBlock; DB procedure call on block (OrderDAO + sql-resources).
