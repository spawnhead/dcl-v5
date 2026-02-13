# Order Edit Screen - Network Payloads (BLOCKED)

## Status: BLOCKED

The exact HTTP request/response wire format has not been captured from a running legacy system.
This document describes what needs to be verified and how to capture the actual payloads.

---

## Missing Network Captures

### Required HAR Captures

| # | Operation | URL | Status |
|---|-----------|-----|--------|
| 1 | Create new order form | `POST /OrderAction.do?dispatch=input` | NOT CAPTURED |
| 2 | Load existing order | `POST /OrderAction.do?dispatch=edit` | NOT CAPTURED |
| 3 | Save order | `POST /OrderAction.do?dispatch=process` | NOT CAPTURED |
| 4 | Reload form | `POST /OrderAction.do?dispatch=reload` | NOT CAPTURED |
| 5 | Print order | `POST /OrderAction.do?dispatch=print` | NOT CAPTURED |
| 6 | Print letter | `POST /OrderAction.do?dispatch=printLetter` | NOT CAPTURED |
| 7 | Add produce | `POST /OrderAction.do?dispatch=newProduce` | NOT CAPTURED |
| 8 | Edit produce | `POST /OrderAction.do?dispatch=editProduce` | NOT CAPTURED |
| 9 | Delete produce | `POST /OrderAction.do?dispatch=deleteProduce` | NOT CAPTURED |
| 10 | Select CP | `POST /OrderAction.do?dispatch=selectCP` | NOT CAPTURED |
| 11 | Import Excel | `POST /OrderAction.do?dispatch=importExcel` | NOT CAPTURED |
| 12 | Upload template | `POST /OrderAction.do?dispatch=uploadTemplate` | NOT CAPTURED |
| 13 | Add attachment | `POST /OrderAction.do?dispatch=deferredAttach` | NOT CAPTURED |
| 14 | Download attachment | `POST /OrderAction.do?dispatch=downloadAttachment` | NOT CAPTURED |
| 15 | Delete attachment | `POST /OrderAction.do?dispatch=deleteAttachment` | NOT CAPTURED |

### Required AJAX Captures

| # | Operation | URL | Status |
|---|-----------|-----|--------|
| 1 | Load payments grid | `POST /OrderAction.do?dispatch=ajaxOrderPaymentsGrid` | NOT CAPTURED |
| 2 | Add payment row | `POST /OrderAction.do?dispatch=ajaxAddToPaymentGrid` | NOT CAPTURED |
| 3 | Remove payment row | `POST /OrderAction.do?dispatch=ajaxRemoveFromPaymentGrid` | NOT CAPTURED |
| 4 | Recalculate payments | `POST /OrderAction.do?dispatch=ajaxRecalculatePaymentGrid` | NOT CAPTURED |
| 5 | Load pay sums grid | `POST /OrderAction.do?dispatch=ajaxOrderPaySumsGrid` | NOT CAPTURED |
| 6 | Add pay sum row | `POST /OrderAction.do?dispatch=ajaxAddToPaySumGrid` | NOT CAPTURED |
| 7 | Remove pay sum row | `POST /OrderAction.do?dispatch=ajaxRemoveFromPaySumsGrid` | NOT CAPTURED |
| 8 | Recalculate pay sums | `POST /OrderAction.do?dispatch=ajaxRecalcPaySumGrid` | NOT CAPTURED |
| 9 | Check contract copy | `POST /OrderAction.do?dispatch=ajaxIsContractCopy` | NOT CAPTURED |
| 10 | Check spec copy | `POST /OrderAction.do?dispatch=ajaxIsSpecificationCopy` | NOT CAPTURED |
| 11 | Check save validation | `POST /OrderAction.do?dispatch=ajaxCheckSave` | NOT CAPTURED |

---

## How to Verify

### Step 1: Prepare Legacy Environment
1. Deploy legacy application locally or access development server
2. Ensure database has test data (see TEST_DATA_SPEC.md)
3. Enable browser DevTools (F12)

### Step 2: Capture Network Traffic
1. Open Chrome/Firefox DevTools
2. Go to Network tab
3. Check "Preserve log"
4. Perform each operation listed above
5. Right-click → "Save all as HAR with content"

### Step 3: Export HAR Files
Save each capture with descriptive filename:
- `order-create-form.har`
- `order-edit-load.har`
- `order-save.har`
- `order-payments-grid.har`
- etc.

### Step 4: Extract Key Information
For each capture, document:
1. Request URL and method
2. Request headers (especially cookies, CSRF tokens)
3. Request body (form data, multipart structure)
4. Response status code
5. Response headers
6. Response body (HTML fragment, JSON, redirect)

---

## Specific Gaps to Address

### 1. Form Field Names
**Unknown**: Exact form field names in POST body

**How to verify**:
1. Create new order
2. Fill all fields
3. Save with DevTools open
4. Check Request Payload in Network tab

**Expected format** (based on JSP analysis):
```
dispatch=process
ord_date=12.02.2026
contractor.id=CNT-001
contact_person.cps_id=CPS-001
sellerForWho.id=SLL-001
...
```

### 2. Session Cookie Handling
**Unknown**: Session cookie name and format

**How to verify**:
1. Check Cookies in Application tab
2. Note JSESSIONID or custom cookie name
3. Verify if cookie is HTTP-only, Secure

### 3. CSRF Token
**Unknown**: If CSRF token is used

**How to verify**:
1. Check if form has hidden token field
2. Check if token is in header or body
3. Note token parameter name

### 4. AJAX Response Format
**Unknown**: Exact HTML structure of AJAX responses

**How to verify**:
1. Trigger AJAX operation (e.g., add payment row)
2. Check Response tab in Network
3. Note HTML structure, table rows, scripts

### 5. File Upload Format
**Unknown**: Multipart structure for attachments

**How to verify**:
1. Upload attachment
2. Check Request Headers for Content-Type: multipart/form-data
3. Note boundary format
4. Note file field name

### 6. Error Response Format
**Unknown**: How errors are returned

**How to verify**:
1. Trigger validation error (e.g., missing required field)
2. Check response format
3. Note if errors are in HTML, JSON, or redirect with flash message

---

## Expected Payload Structure (Based on Code Analysis)

### Save Order Request (Estimated)
```
POST /OrderAction.do HTTP/1.1
Content-Type: application/x-www-form-urlencoded

dispatch=process
is_new_doc=true
ord_date=12.02.2026
sellerForWho.id=SLL-001
contractor.id=CNT-001
contact_person.cps_id=CPS-001
blank.bln_id=BLN-001
contractor_for.id=CNT-003
contract.con_id=CON-001
specification.spc_id=SPC-001
stuffCategory.id=STC-001
currency.id=CUR-EUR
ord_pay_condition=Prepayment 50%
deliveryCondition.id=INC-DDP
ord_addr=Minsk, Belarus
ord_delivery_term=30 days
ord_add_info=
ord_discount_all=
ord_discount=0
ord_include_nds=1
ord_nds_rate=20
ord_count_itog_flag=1
ord_add_reduction_flag=
ord_add_reduction=0
ord_add_red_pre_pay_flag=
ord_add_red_pre_pay=0
ord_all_include_in_spec=
ord_annul=
ord_in_one_spec=1
ord_donot_calculate_netto=1
ord_by_guaranty=
ord_delivery_cost_by=
ord_delivery_cost=0
ord_comment=
ord_comment_covering_letter=
director.usr_id=USR-DIR-001
logist.usr_id=USR-LOG-001
director_rb.usr_id=USR-DIRRB-001
chief_dep.usr_id=
manager.usr_id=USR-TEST-002
ord_logist_signature=1
ord_director_rb_signature=1
ord_chief_dep_signature=1
ord_manager_signature=1
ord_print_scale=100
ord_letter_scale=100
show_unit=
merge_positions=1
orderPayments[0].orp_percent=100
orderPayments[0].orp_sum=1500.00
orderPayments[0].orp_date=28.02.2026
orderPaySums[0].ops_sum=1500.00
orderPaySums[0].ops_date=28.02.2026
```

### AJAX Payments Grid Response (Estimated)
```html
<table class="grid-table" id="orderPaymentsGrid">
  <tr class="grid-header">
    <th>%</th>
    <th>Sum</th>
    <th>Date</th>
    <th>Description</th>
    <th>Actions</th>
  </tr>
  <tr class="grid-row">
    <td><input type="text" name="orderPayments[0].orp_percent" value="100"></td>
    <td><input type="text" name="orderPayments[0].orp_sum" value="1500,00"></td>
    <td><input type="text" name="orderPayments[0].orp_date" value="28.02.2026"></td>
    <td>100% - 1500,00 EUR - 28.02.2026</td>
    <td>
      <button onclick="addToPaymentGrid()">+</button>
      <button onclick="removeFromPaymentGrid(0)">-</button>
    </td>
  </tr>
</table>
```

---

## Verification Checklist

- [ ] Capture all main form operations
- [ ] Capture all AJAX operations
- [ ] Document session handling
- [ ] Document CSRF token (if any)
- [ ] Document error response format
- [ ] Document file upload format
- [ ] Compare estimated payloads with actual
- [ ] Update CONTRACTS.md with verified data
- [ ] Create actual payload JSON files

---

## Files to Create After Verification

1. `open-request.json` - Request for opening edit form
2. `open-response.json` - Response with form data
3. `save-request.json` - Request for saving order
4. `save-response.json` - Response after save
5. `payments-grid-request.json` - AJAX request for payments
6. `payments-grid-response.html` - AJAX response HTML
7. `pay-sums-grid-request.json` - AJAX request for pay sums
8. `pay-sums-grid-response.html` - AJAX response HTML
9. `validation-error-response.html` - Error response example
10. `attachment-upload-request.txt` - Multipart request example
