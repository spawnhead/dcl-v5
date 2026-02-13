# Order Edit Screen - API CONTRACTS

## Endpoints Overview

| Endpoint | Method | Dispatch | Description |
|----------|--------|----------|-------------|
| `/OrderAction.do` | POST | input | Create new order form |
| `/OrderAction.do` | POST | edit | Load existing order |
| `/OrderAction.do` | POST | clone | Clone order |
| `/OrderAction.do` | POST | process | Save order |
| `/OrderAction.do` | POST | reload | Refresh form |
| `/OrderAction.do` | POST | print | Save and print |
| `/OrderAction.do` | POST | printLetter | Save and print letter |
| `/OrderAction.do` | POST | back | Cancel and return |
| `/OrderAction.do` | POST | newProduce | Add produce line |
| `/OrderAction.do` | POST | editProduce | Edit produce line |
| `/OrderAction.do` | POST | deleteProduce | Delete produce line |
| `/OrderAction.do` | POST | selectCP | Select Commercial Proposal |
| `/OrderAction.do` | POST | importExcel | Import from Excel |
| `/OrderAction.do` | POST | uploadTemplate | Upload template |

## AJAX Endpoints

| Endpoint | Method | Parameters | Description |
|----------|--------|------------|-------------|
| `/OrderAction.do` | POST | dispatch=ajaxOrderPaymentsGrid | Get payments grid |
| `/OrderAction.do` | POST | dispatch=ajaxAddToPaymentGrid | Add payment row |
| `/OrderAction.do` | POST | dispatch=ajaxRemoveFromPaymentGrid&id={idx} | Remove payment row |
| `/OrderAction.do` | POST | dispatch=ajaxRecalculatePaymentGrid | Recalculate payments |
| `/OrderAction.do` | POST | dispatch=ajaxOrderPaySumsGrid | Get pay sums grid |
| `/OrderAction.do` | POST | dispatch=ajaxAddToPaySumGrid | Add pay sum row |
| `/OrderAction.do` | POST | dispatch=ajaxRemoveFromPaySumsGrid&id={idx} | Remove pay sum row |
| `/OrderAction.do` | POST | dispatch=ajaxRecalcPaySumGrid | Recalculate pay sums |
| `/OrderAction.do` | POST | dispatch=ajaxIsContractCopy&contract-id={id} | Check if contract is copy |
| `/OrderAction.do` | POST | dispatch=ajaxIsSpecificationCopy&specification-id={id} | Check if spec is copy |
| `/OrderAction.do` | POST | dispatch=ajaxCheckSave | Validate before save |

---

## 1. Create New Order (input)

### Request
```
POST /OrderAction.do
Content-Type: application/x-www-form-urlencoded

dispatch=input
```

### Response
- **Type**: HTML page redirect to Order.jsp
- **Form Initialization**:
  - `ord_date` = current date
  - `is_new_doc` = "true"
  - `director` = loaded from config `order.director`
  - `logist` = loaded from config `order.logist`
  - `director_rb` = loaded from config `order.directorRB`
  - `manager` = current user
  - `ord_donot_calculate_netto` = "1"
  - `ord_in_one_spec` = "1"
  - `merge_positions` = "1"
  - All signature flags = "1"
  - `currency` = default from message `Order.default_currency`
  - `orderPayments` = [one row with 100%]
  - `orderPaySums` = [one empty row]

---

## 2. Edit Existing Order (edit)

### Request
```
POST /OrderAction.do
Content-Type: application/x-www-form-urlencoded

dispatch=edit
ord_id={orderId}
```

### Response
- **Type**: HTML page with Order.jsp
- **Form Populated**: All fields from database
- **Additional Loads**:
  - Order produces with calculations
  - Order payments
  - Order pay sums
  - Attachments list

---

## 3. Save Order (process)

### Request
```
POST /OrderAction.do
Content-Type: application/x-www-form-urlencoded

dispatch=process
is_new_doc={true|empty}
ord_id={id}
ord_number={number}
ord_date={dd.MM.yyyy}
sellerForWho.id={sellerId}
contractor.id={contractorId}
contact_person.cps_id={contactPersonId}
blank.bln_id={blankId}
contractor_for.id={contractorForId}
contract.con_id={contractId}
specification.spc_id={specId}
stuffCategory.id={categoryId}
currency.id={currencyId}
ord_sent_to_prod_date={dd.MM.yyyy}
ord_received_conf_date={dd.MM.yyyy}
ord_num_conf={confNumber}
ord_date_conf={dd.MM.yyyy}
ord_date_conf_all={1|empty}
ord_conf_sent_date={dd.MM.yyyy}
ord_ready_for_deliv_date={dd.MM.yyyy}
ord_ready_for_deliv_date_all={1|empty}
shippingDocType.id={docTypeId}
ord_shp_doc_number={docNumber}
ord_ship_from_stock={1|empty}
ord_arrive_in_lithuania={dd.MM.yyyy}
ord_executed_date={dd.MM.yyyy}
ord_pay_condition={text}
deliveryCondition.id={incoTermId}
ord_addr={address}
ord_delivery_term={term}
ord_add_info={info}
ord_discount_all={1|empty}
ord_discount={amount}
ord_include_nds={1|empty}
ord_nds_rate={rate}
ord_count_itog_flag={1|empty}
ord_add_reduction_flag={1|empty}
ord_add_reduction={amount}
ord_add_red_pre_pay_flag={1|empty}
ord_add_red_pre_pay={amount}
ord_all_include_in_spec={1|empty}
ord_annul={1|empty}
ord_in_one_spec={1|empty}
ord_donot_calculate_netto={1|empty}
ord_by_guaranty={1|empty}
ord_delivery_cost_by={1|empty}
ord_delivery_cost={cost}
ord_comment={comment}
ord_comment_covering_letter={letter}
director.usr_id={directorId}
logist.usr_id={logistId}
director_rb.usr_id={directorRbId}
chief_dep.usr_id={chiefId}
manager.usr_id={managerId}
ord_logist_signature={1|empty}
ord_director_rb_signature={1|empty}
ord_chief_dep_signature={1|empty}
ord_manager_signature={1|empty}
ord_print_scale={scale}
ord_letter_scale={scale}
show_unit={1|empty}
merge_positions={1|empty}
```

### Response (Success)
- **Redirect**: `/OrdersAction.do` (back to list)

### Response (Validation Error)
- **Type**: HTML page with Order.jsp
- **Errors**: Displayed via `<html:errors/>`

---

## 4. AJAX: Order Payments Grid

### Get Grid (ajaxOrderPaymentsGrid)
```
POST /OrderAction.do
Content-Type: application/x-www-form-urlencoded

dispatch=ajaxOrderPaymentsGrid
```

**Response**: HTML fragment with payments grid table

### Add Row (ajaxAddToPaymentGrid)
```
POST /OrderAction.do
Content-Type: application/x-www-form-urlencoded

dispatch=ajaxAddToPaymentGrid
```

**Response**: HTML fragment with updated payments grid

### Remove Row (ajaxRemoveFromPaymentGrid)
```
POST /OrderAction.do
Content-Type: application/x-www-form-urlencoded

dispatch=ajaxRemoveFromPaymentGrid
id={rowIndex}
```

**Response**: HTML fragment with updated payments grid

### Recalculate (ajaxRecalculatePaymentGrid)
```
POST /OrderAction.do
Content-Type: application/x-www-form-urlencoded

dispatch=ajaxRecalculatePaymentGrid
orderPayments[0].orp_percent={percent}
orderPayments[0].orp_sum={sum}
orderPayments[0].orp_date={dd.MM.yyyy}
... (all payment rows)
```

**Response**: HTML fragment with recalculated payments grid

---

## 5. AJAX: Order Pay Sums Grid

### Get Grid (ajaxOrderPaySumsGrid)
```
POST /OrderAction.do
Content-Type: application/x-www-form-urlencoded

dispatch=ajaxOrderPaySumsGrid
```

**Response**: HTML fragment with pay sums grid table

### Add Row (ajaxAddToPaySumGrid)
```
POST /OrderAction.do
Content-Type: application/x-www-form-urlencoded

dispatch=ajaxAddToPaySumGrid
```

**Response**: HTML fragment with updated pay sums grid

### Remove Row (ajaxRemoveFromPaySumsGrid)
```
POST /OrderAction.do
Content-Type: application/x-www-form-urlencoded

dispatch=ajaxRemoveFromPaySumsGrid
id={rowIndex}
```

**Response**: HTML fragment with updated pay sums grid

---

## 6. AJAX: Check Contract Copy

### Request
```
POST /OrderAction.do
Content-Type: application/x-www-form-urlencoded

dispatch=ajaxIsContractCopy
contract-id={contractId}
```

### Response
- **Type**: `text/plain`
- **Values**:
  - `Order.project` - if contract is project
  - `Order.copy` - if contract is copy
  - Empty - if neither

---

## 7. AJAX: Check Specification Copy

### Request
```
POST /OrderAction.do
Content-Type: application/x-www-form-urlencoded

dispatch=ajaxIsSpecificationCopy
specification-id={specificationId}
```

### Response
- **Type**: `text/plain`
- **Values**:
  - `Order.project` - if specification is project
  - `Order.copy` - if specification is copy
  - Empty - if neither

---

## 8. AJAX: Check Save Validation

### Request
```
POST /OrderAction.do
Content-Type: application/x-www-form-urlencoded

dispatch=ajaxCheckSave
```

### Response
- **Type**: `text/plain`
- **Values**:
  - `msg.order.empty_table` - if no produce lines
  - `msg.order_produce.check_dlr_price` - if DRP price coefficient > 1.5
  - Empty - if validation passes

---

## 9. Add Produce Line (newProduce)

### Request
```
POST /OrderAction.do
Content-Type: application/x-www-form-urlencoded

dispatch=newProduce
```

### Response
- **Redirect**: OrderProduce.jsp (produce edit dialog)

---

## 10. Edit Produce Line (editProduce)

### Request
```
POST /OrderAction.do
Content-Type: application/x-www-form-urlencoded

dispatch=editProduce
number={rowNumber}
```

### Response
- **Redirect**: OrderProduce.jsp with produce data loaded

---

## 11. Delete Produce Line (deleteProduce)

### Request
```
POST /OrderAction.do
Content-Type: application/x-www-form-urlencoded

dispatch=deleteProduce
number={rowNumber}
```

### Response
- **Type**: HTML page with Order.jsp
- **Effect**: Produce line removed from session order

---

## 12. Select Commercial Proposal (selectCP)

### Request
```
POST /OrderAction.do
Content-Type: application/x-www-form-urlencoded

dispatch=selectCP
```

### Response
- **Redirect**: Commercial Proposal selection screen

---

## 13. Import from Excel (importExcel)

### Request
```
POST /OrderAction.do
Content-Type: application/x-www-form-urlencoded

dispatch=importExcel
```

### Response
- **Redirect**: Excel import screen

---

## 14. Upload Template (uploadTemplate)

### Request
```
POST /OrderAction.do
Content-Type: application/x-www-form-urlencoded

dispatch=uploadTemplate
```

### Response
- **Redirect**: Template upload screen

---

## 15. Print Order (print)

### Request
```
POST /OrderAction.do
Content-Type: application/x-www-form-urlencoded

dispatch=print
```

### Response
- **Type**: HTML page with print trigger
- **Effect**: Sets `needPrint=true`, triggers PDF generation

---

## 16. Print Covering Letter (printLetter)

### Request
```
POST /OrderAction.do
Content-Type: application/x-www-form-urlencoded

dispatch=printLetter
```

### Response
- **Type**: HTML page with print trigger
- **Effect**: Sets `needPrintLetter=true`, triggers PDF generation

---

## 17. Attachment Operations

### Add Attachment (deferredAttach)
```
POST /OrderAction.do
Content-Type: application/x-www-form-urlencoded

dispatch=deferredAttach
```

### Download Attachment (downloadAttachment)
```
POST /OrderAction.do
Content-Type: application/x-www-form-urlencoded

dispatch=downloadAttachment
attachmentId={attachmentId}
```

**Response**: File download stream

### Delete Attachment (deleteAttachment)
```
POST /OrderAction.do
Content-Type: application/x-www-form-urlencoded

dispatch=deleteAttachment
attachmentId={attachmentId}
```

**Response**: HTML page with Order.jsp, attachment removed

---

## BLOCKED: Wire Format Not Confirmed

### Missing Network Capture
The exact HTTP request/response wire format has not been captured from a running legacy system.

### How to Verify
1. Start legacy application with debugging enabled
2. Open browser DevTools Network tab
3. Perform each operation (create, edit, save, AJAX calls)
4. Export HAR file with full request/response bodies
5. Compare with this specification

### Specific Gaps
- [ ] Exact form field names in POST body
- [ ] Order of form fields in request
- [ ] Session cookie handling
- [ ] CSRF token (if any)
- [ ] AJAX response HTML structure
- [ ] Error message format in AJAX responses
- [ ] File upload multipart format for attachments

### Required HAR Capture Points
1. `POST /OrderAction.do?dispatch=input` - New order form
2. `POST /OrderAction.do?dispatch=edit` - Load existing
3. `POST /OrderAction.do?dispatch=process` - Save
4. `POST /OrderAction.do?dispatch=ajaxOrderPaymentsGrid` - Payments AJAX
5. `POST /OrderAction.do?dispatch=ajaxOrderPaySumsGrid` - Pay sums AJAX
6. `POST /OrderAction.do?dispatch=ajaxCheckSave` - Validation AJAX
