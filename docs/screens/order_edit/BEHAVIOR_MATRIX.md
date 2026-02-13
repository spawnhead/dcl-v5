# Order Edit Screen - BEHAVIOR MATRIX

## Overview
This matrix documents all behavioral variations of the Order Edit screen based on:
- Document state (new/existing/blocked/annulled)
- User role
- Field dependencies
- Business rules

---

## 1. Document State Matrix

| State | ord_id | ord_block | ord_annul | Form Mode | Editable Fields |
|-------|--------|-----------|-----------|-----------|-----------------|
| New | empty | empty | empty | Edit | All (except ord_number) |
| Existing Unblocked | set | empty/null | empty | Edit | All (except ord_number) |
| Existing Blocked | set | "1" | empty | Readonly | None (print only) |
| Annulled | set | "1" | "1" | Readonly | None (print only) |

---

## 2. Role-Based Field Access Matrix

### 2.1 Header Section (Above Logistic)

| Field | Admin | Economist | Logistic | Manager | UserInLithuania |
|-------|-------|-----------|----------|---------|-----------------|
| sellerForWho | E | E | E | E | R |
| ord_number | R | R | R | R | R |
| contractor | E | E | E | E | R |
| contact_person | E | E | E | E | R |
| ord_date | E | E | E | E | R |
| blank | E | E | E | E | R |
| contractor_for | E | E | E | E | R |
| contract | E | E | E | E | R |
| specification | E | E | E | E | R |
| stuffCategory | E | E | E | E | R |
| currency | E | E | E | E | R |

**Legend**: E = Editable, R = Readonly, H = Hidden

### 2.2 Logistic Section

| Field | Admin | Economist | Logistic | Manager | UserInLithuania |
|-------|-------|-----------|----------|---------|-----------------|
| ord_sent_to_prod_date | E | E | E | E | R |
| ord_received_conf_date | E | E | E | R | R |
| ord_num_conf | E | E | E | R | R |
| ord_date_conf | E | E | E | R | R |
| ord_date_conf_all | E | E | E | R | R |
| ord_conf_sent_date | E | E | E | R | R |
| ord_ready_for_deliv_date | E | E | E | R | R |
| ord_ready_for_deliv_date_all | E | E | E | R | R |
| shippingDocType | E | E | E | R | R |
| ord_shp_doc_number | E | E | E | R | R |
| ord_ship_from_stock | E | E | E | R | R |
| ord_arrive_in_lithuania | E | E | E | R | E |
| ord_executed_date | E | E | E | R | R |

### 2.3 Payment Conditions Section

| Field | Admin | Economist | Logistic | Manager | UserInLithuania |
|-------|-------|-----------|----------|---------|-----------------|
| ord_pay_condition | E | E | E | E | R |
| deliveryCondition | E | E | E | E | R |
| ord_addr | E | E | E | E | R |
| ord_delivery_term | E | E | E | E | R |
| ord_add_info | E | E | E | E | R |

### 2.4 Additional Options Section

| Field | Admin | Economist | Logistic | Manager | UserInLithuania |
|-------|-------|-----------|----------|---------|-----------------|
| ord_discount_all | E | E | E | E | R |
| ord_discount | E | E | E | E | R |
| ord_include_nds | E | E | E | E | R |
| ord_nds_rate | E | E | E | E | R |
| ord_count_itog_flag | E | E | E | E | R |
| ord_add_reduction_flag | E | E | E | E | R |
| ord_add_reduction | E | E | E | E | R |
| ord_add_red_pre_pay_flag | E | E | E | E | R |
| ord_add_red_pre_pay | E | E | E | E | R |
| ord_all_include_in_spec | E | E | E | E | R |
| ord_annul | E | E | R | R | R |
| ord_in_one_spec | E | E | E | E | R |
| ord_donot_calculate_netto | E | E | E | E | R |
| ord_by_guaranty | E | E | E | E | R |
| ord_delivery_cost_by | E | E | E | E | R |
| ord_delivery_cost | E | E | E | E | R |
| ord_comment | E | E | E | E | R |

### 2.5 Covering Letter Section

| Field | Admin | Economist | Logistic | Manager | UserInLithuania |
|-------|-------|-----------|----------|---------|-----------------|
| ord_comment_covering_letter | E | E | E | E | R |
| director | E | E | E | E | R |
| logist | E | E | E | E | R |
| director_rb | E | E | E | E | R |
| chief_dep | E | E | E | E | R |
| manager | E | E | E | E | R |
| Signature checkboxes | E | E | E | E | R |

### 2.6 Print Parameters Section

| Field | Admin | Economist | Logistic | Manager | UserInLithuania |
|-------|-------|-----------|----------|---------|-----------------|
| ord_print_scale | E | E | E | E | E |
| ord_letter_scale | E | E | E | E | E |
| show_unit | E | E | E | E | E |
| merge_positions | E | E | E | E | E |

### 2.7 Attachments Section

| Action | Admin | Economist | Logistic | Manager | UserInLithuania |
|--------|-------|-----------|----------|---------|-----------------|
| View attachments | Y | Y | Y | Y | Y |
| Add attachment | Y | Y | Y | Y | N |
| Download attachment | Y | Y | Y | Y | Y |
| Delete attachment | Y | Y | Y | Y | N |

---

## 3. Produces Grid Action Matrix

### 3.1 By Document State

| Action | New | Unblocked | Blocked (date_conf_all set) | Blocked (date_conf_all empty) |
|--------|-----|-----------|----------------------------|------------------------------|
| Add | Y | Y | N | N |
| Edit | Y | Y | N | Y |
| Clone | Y | Y | N | N |
| Delete | Y | Y | N | N |

### 3.2 By Row State

| Action | opr_count_executed = 0 | opr_count_executed > 0 | opr_occupied set |
|--------|------------------------|------------------------|------------------|
| Edit | Y | Y | Y |
| Clone | Y | Y | Y |
| Delete | Y | N | N |

### 3.3 By Role (Unblocked Document)

| Action | Admin | Economist | Logistic | Manager |
|--------|-------|-----------|----------|---------|
| Add | Y | Y | N | Y |
| Edit | Y | Y | N | Y |
| Clone | Y | Y | N | Y |
| Delete | Y | Y | N | Y |

---

## 4. Button Visibility Matrix

| Button | New | Unblocked | Blocked | UserInLithuania |
|--------|-----|-----------|---------|-----------------|
| Save | Y | Y | N | N |
| Print | Y | Y | Y | Y |
| Print Letter | Y | Y | Y | Y |
| Cancel | Y | Y | Y | Y |
| Add Produce | Y | Y | N | N |
| Select CP | Y | Y | N | N |
| Import Excel | Y | Y | N | N |
| Upload Template | Y | Y | N | N |
| Executed Count | Y | Y | Y | N |
| Add Attachment | Y | Y | N | N |

---

## 5. Field Dependency Matrix

### 5.1 Contact Person Dependency

| Condition | Effect |
|-----------|--------|
| contractor changed | contact_person cleared |
| contractor empty | contact_person disabled |

### 5.2 Contract/Specification Dependency

| Condition | Effect |
|-----------|--------|
| contractor_for changed | contract cleared, specification cleared |
| contract changed | specification filtered by contract |
| contract cleared | specification cleared |

### 5.3 Date Conf All Dependency

| Condition | Effect |
|-----------|--------|
| ord_date_conf_all unchecked | ord_ready_for_deliv_date_all cleared |
| ord_date_conf_all checked | ord_date_conf required on save |

### 5.4 Ready for Delivery All Dependency

| Condition | Effect |
|-----------|--------|
| ord_ready_for_deliv_date_all unchecked | ord_ready_for_deliv, shippingDocType, ord_shp_doc_number, ord_ship_from_stock, ord_arrive_in_lithuania cleared |
| ord_ready_for_deliv_date_all checked | All above fields required on save |

### 5.5 In One Spec Dependency

| Condition | Effect |
|-----------|--------|
| ord_in_one_spec checked | contractor_for, contract, specification required |
| ord_in_one_spec unchecked | contractor_for, contract, specification optional |

---

## 6. Validation Matrix

### 6.1 Save Validation (Blocking)

| Rule | Condition | Error Message |
|------|-----------|---------------|
| V1 | ord_in_one_spec AND (contractor_for empty OR contract empty OR specification empty) | error.order.ord_in_one_spec |
| V2 | Any logistic date set without previous dates | error.order.queue_date |
| V3 | ord_received_conf_date < ord_sent_to_prod_date | error.order.ord_received_conf_date |
| V4 | ord_conf_sent_date < ord_received_conf_date | error.order.ord_conf_sent_date |
| V5 | ord_ready_for_deliv_date < ord_received_conf_date | error.order.ord_ready_for_deliv_date |
| V6 | ord_executed_date < ord_ready_for_deliv_date | error.order.ord_executed_date |
| V7 | Produce without product (non-admin) | error.order.ord_null_produce |
| V8 | ord_all_include_in_spec AND NOT ord_by_guaranty AND drp_price <= 0 | error.order.null_drp_price |
| V9 | ord_date_conf_all AND ord_date_conf empty | error.order.not_entered_date_conf |
| V10 | ord_ready_for_deliv_date_all AND (ord_ready_for_deliv_date empty OR shippingDocType empty OR ord_shp_doc_number empty) | error.order.not_entered_ready_for_deliv_fields |
| V11 | ord_executed_date < any line executed date | error.order.ordExecutedDateInLine |

### 6.2 Save Validation (Warning - Non-blocking)

| Rule | Condition | Warning Message |
|------|-----------|-----------------|
| W1 | All counts executed AND ord_executed_date empty AND NOT ord_annul | error.order.not_entered_executed_date |
| W2 | No produce lines | msg.order.empty_table |
| W3 | DRP price coefficient > 1.5 | msg.order_produce.check_dlr_price |

---

## 7. AJAX Grid Behavior Matrix

### 7.1 Payments Grid

| Action | Trigger | Request Params | Response |
|--------|---------|----------------|----------|
| Load | Form load | None | HTML fragment |
| Add Row | Click "+" button | dispatch=ajaxAddToPaymentGrid | HTML fragment with new row |
| Remove Row | Click "-" button | dispatch=ajaxRemoveFromPaymentGrid, id={idx} | HTML fragment without row |
| Recalculate | Change percent/sum | dispatch=ajaxRecalculatePaymentGrid, all row values | HTML fragment with recalculated |

### 7.2 Pay Sums Grid

| Action | Trigger | Request Params | Response |
|--------|---------|----------------|----------|
| Load | Form load | None | HTML fragment |
| Add Row | Click "+" button | dispatch=ajaxAddToPaySumGrid | HTML fragment with new row |
| Remove Row | Click "-" button | dispatch=ajaxRemoveFromPaySumsGrid, id={idx} | HTML fragment without row |
| Recalculate | Change sum | dispatch=ajaxRecalcPaySumGrid, all row values | HTML fragment with recalculated |

---

## 8. Number Generation Matrix

| Seller Prefix | Year | Month | Sequence | User Code | Result |
|---------------|------|-------|----------|-----------|--------|
| DCL | 26 | 02 | 0001 | IVAN | DCL-2602/0001-IVAN |
| SAM | 26 | 12 | 0123 | PETR | SAM-2612/0123-PETR |

**Generation Rules**:
1. Prefix from `sellerForWho.prefixForOrder`
2. Year = last 2 digits of `ord_date` year
3. Month = 2 digits of `ord_date` month
4. Sequence = 4-digit padded from `get-num_order` generator
5. User code = uppercase `usr_code` from current user

---

## 9. Block State Transition Matrix

| Current State | Trigger | New State | Side Effects |
|---------------|---------|-----------|--------------|
| Unblocked | All counts executed AND ord_executed_date set | Blocked | Form becomes readonly |
| Unblocked | ord_annul checked AND saved | Blocked | Form becomes readonly |
| Blocked | (none) | Blocked | Cannot unblock |

---

## 10. Print Behavior Matrix

| Print Type | Pre-condition | Save Required | Scale Field |
|------------|---------------|---------------|-------------|
| Order | Form valid | Yes (if new) | ord_print_scale |
| Covering Letter | Form valid | Yes (if new) | ord_letter_scale |

**Print Flow**:
1. Validate form
2. Save order (if new or changed)
3. Save scale value
4. Set needPrint/needPrintLetter flag
5. Render form with print trigger
6. PDF opens in new window

---

## 11. Clone Behavior Matrix

| Source Field | Cloned Value | Notes |
|--------------|--------------|-------|
| ord_id | empty | New ID generated on save |
| ord_number | empty | Generated on save |
| ord_date | current date | Reset to today |
| ord_block | empty | Unblocked |
| ord_annul | empty | Not annulled |
| All logistic dates | empty | Reset |
| contractor_for | empty | Reset |
| specification | empty | Reset |
| shippingDocType | empty | Reset |
| seller | empty | Reset |
| ord_comment | empty | Reset |
| ord_comment_covering_letter | empty | Reset |
| produces | copied | All lines copied |
| orderPayments | reset | Single 100% row |
| orderPaySums | reset | Single empty row |
| usr_date_create | empty | Reset |
| usr_date_edit | empty | Reset |
| createUser | current user | Set to cloner |
| editUser | current user | Set to cloner |

---

## 12. Session State Matrix

| Key | Set By | Used By | Cleared By |
|-----|--------|---------|------------|
| Order.class | input, edit, clone | All operations | back, process |
| DeferredAttachmentService | input, edit | Attachment ops | back, process |
| Contractor.currentContractorId | newContactPerson | retFromContractor | retFromContractor |
| ContactPerson.current_contact_person_id | ContactPerson edit | retFromContractor | retFromContractor |
| newContractorFor | newContractorFor | retFromContractor | retFromContractor |
