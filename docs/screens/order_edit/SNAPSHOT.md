# Order Edit Screen - SNAPSHOT

## Screen Identity
- **Screen Name**: Order Edit (Create/Edit Order)
- **Legacy JSP**: `src/main/webapp/jsp/Order.jsp`
- **Struts Action**: `/OrderAction` (session-scoped)
- **Action Class**: `net.sam.dcl.action.OrderAction`
- **Form Bean**: `net.sam.dcl.form.OrderForm`
- **Mode**: Create + Edit (unified screen)

## Source Trace
| Aspect | Legacy Source |
|--------|---------------|
| Main JSP | `src/main/webapp/jsp/Order.jsp` (2268 lines) |
| Action | `src/main/java/net/sam/dcl/action/OrderAction.java` |
| Produce Dialog | `src/main/webapp/jsp/OrderProduce.jsp` |
| Payments Grid (AJAX) | `src/main/webapp/ajax/OrderPaymentsGrid.jsp` |
| Pay Sums Grid (AJAX) | `src/main/webapp/ajax/OrderPaySumsGrid.jsp` |
| Struts Config | `struts-config.xml` - `/OrderAction` mapping |

## Entry Points
1. **Create New**: `OrderAction.input()` - from Orders list "New" button
2. **Edit Existing**: `OrderAction.edit()` - from Orders list row click
3. **Clone**: `OrderAction.clone()` - from Orders list "Clone" button
4. **From CP Selection**: `OrderAction.returnFromSelectCP()` - after selecting Commercial Proposal

## Screen Sections

### 1. Header Section
| Field | Type | Source Binding | Notes |
|-------|------|----------------|-------|
| sellerForWho | ServerList (Seller) | `form.sellerForWho` | Seller prefix used in order number |
| ord_number | Text (readonly) | `form.ord_number` | Auto-generated: `{prefix}-{YYMM}/{num}-{usr_code}` |
| contractor | ServerList (Contractor) | `form.contractor` | Required for save |
| contact_person | ServerList (ContactPerson) | `form.contact_person` | Filtered by contractor |
| ord_date | Date | `form.ord_date` | Default: current date |
| blank | ServerList (Blank) | `form.blank` | Print template |

### 2. Produce Selection Section (Before Table)
| Field | Type | Source Binding | Notes |
|-------|------|----------------|-------|
| contractor_for | ServerList (Contractor) | `form.contractor_for` | "For" contractor |
| contract | ServerList (Contract) | `form.contract` | Filtered by contractor_for |
| specification | ServerList (Specification) | `form.specification` | Filtered by contract |
| stuffCategory | ServerList (StuffCategory) | `form.stuffCategory` | Product category filter |
| currency | ServerList (Currency) | `form.currency` | Order currency |

### 3. Produces Table (Main Grid)
**Grid ID**: `orderProducesGrid`
**Columns**:
| Column | Field | Type | Editable | Notes |
|--------|-------|------|----------|-------|
| № | number | Text | N | Row number |
| Image | produceImage | Image | N | Product image |
| Catalog № | catalogNumber | Link | N | Opens produce card |
| Product Name | produceFullName | Link | N | Opens produce card |
| Unit | unitName | Text | N | From produce |
| Count | opr_count | Input | Y | Quantity |
| Price brutto | opr_price_brutto | Input | Y | Gross price |
| Discount | opr_discount | Input | Y | Discount % |
| Price netto | opr_price_netto | Text | N | Calculated |
| NDS | opr_nds | Text | N | Calculated VAT |
| Sum netto | opr_sum_netto | Text | N | Line total netto |
| Sum brutto | opr_sum_brutto | Text | N | Line total brutto |
| Executed | opr_count_executed | Text | N | Fulfilled quantity |
| DRP Price | drp_price | Input | Y | Dealer retail price |
| Comment | opr_comment | Input | Y | Line comment |
| Actions | - | Buttons | - | Edit, Clone, Delete |

**Grid Actions**:
- `newProduce` - Add new line
- `editProduce` - Edit line (opens OrderProduce.jsp dialog)
- `cloneProduce` - Clone line
- `deleteProduce` - Delete line
- `selectCP` - Import from Commercial Proposal
- `importExcel` - Import from Excel
- `uploadTemplate` - Upload Excel template

### 4. Logistic Section (Role: Logist/Admin/Economist)
| Field | Type | Source Binding | Notes |
|-------|------|----------------|-------|
| ord_sent_to_prod_date | Date | `form.ord_sent_to_prod_date` | Sent to production |
| ord_received_conf_date | Date | `form.ord_received_conf_date` | Confirmation received |
| ord_num_conf | Text | `form.ord_num_conf` | Confirmation number |
| ord_date_conf | Date | `form.ord_date_conf` | Confirmation date |
| ord_date_conf_all | Checkbox | `form.ord_date_conf_all` | "Same date for all" |
| ord_conf_sent_date | Date | `form.ord_conf_sent_date` | Confirmation sent |
| ord_ready_for_deliv_date | Date | `form.ord_ready_for_deliv_date` | Ready for delivery |
| ord_ready_for_deliv_date_all | Checkbox | `form.ord_ready_for_deliv_date_all` | "All ready at once" |
| shippingDocType | ServerList | `form.shippingDocType` | Shipping document type |
| ord_shp_doc_number | Text | `form.ord_shp_doc_number` | Shipping doc number |
| ord_ship_from_stock | Checkbox | `form.ord_ship_from_stock` | Ship from stock |
| ord_arrive_in_lithuania | Date | `form.ord_arrive_in_lithuania` | Arrive in Lithuania |
| ord_executed_date | Date | `form.ord_executed_date` | Execution date |

### 5. Payment Conditions Section
| Field | Type | Source Binding | Notes |
|-------|------|----------------|-------|
| ord_pay_condition | Textarea | `form.ord_pay_condition` | Payment conditions text |
| deliveryCondition | ServerList (IncoTerm) | `form.deliveryCondition` | Delivery terms |
| ord_addr | Text | `form.ord_addr` | Delivery address |
| ord_delivery_term | Text | `form.ord_delivery_term` | Delivery term |
| ord_add_info | Textarea | `form.ord_add_info` | Additional info |

### 6. Order Payments Grid (AJAX)
**Grid ID**: `orderPayments`
**AJAX URL**: `OrderAction.ajaxOrderPaymentsGrid()`
**Columns**:
| Column | Field | Type |
|--------|-------|------|
| Percent | orp_percent | Input |
| Sum | orp_sum | Input |
| Date | orp_date | Input |
| Description | orp_description | Text (calculated) |

**AJAX Actions**:
- `addToPaymentGrid` - Add row
- `removeFromPaymentGrid` - Remove row (param: `id`)
- `recalculatePaymentGrid` - Recalculate

### 7. Order Pay Sums Grid (AJAX)
**Grid ID**: `orderPaySums`
**AJAX URL**: `OrderAction.ajaxOrderPaySumsGrid()`
**Columns**:
| Column | Field | Type |
|--------|-------|------|
| Sum | ops_sum | Input |
| Date | ops_date | Input |

**AJAX Actions**:
- `addToPaymSumGrid` - Add row
- `removeFromPaySumGrid` - Remove row (param: `id`)
- `recalcPaySumGrid` - Recalculate

### 8. Additional Options Section
| Field | Type | Source Binding | Notes |
|-------|------|----------------|-------|
| ord_discount_all | Checkbox | `form.ord_discount_all` | Apply discount to all |
| ord_discount | Input | `form.ord_discount` | Total discount |
| ord_include_nds | Checkbox | `form.ord_include_nds` | Include NDS |
| ord_nds_rate | Input | `form.ord_nds_rate` | NDS rate % |
| ord_count_itog_flag | Checkbox | `form.ord_count_itog_flag` | Count totals |
| ord_add_reduction_flag | Checkbox | `form.ord_add_reduction_flag` | Add reduction |
| ord_add_reduction | Input | `form.ord_add_reduction` | Reduction amount |
| ord_add_red_pre_pay_flag | Checkbox | `form.ord_add_red_pre_pay_flag` | Pre-pay reduction |
| ord_add_red_pre_pay | Input | `form.ord_add_red_pre_pay` | Pre-pay reduction amount |
| ord_all_include_in_spec | Checkbox | `form.ord_all_include_in_spec` | All in specification |
| ord_annul | Checkbox | `form.ord_annul` | Annulled |
| ord_in_one_spec | Checkbox | `form.ord_in_one_spec` | In one specification |
| ord_donot_calculate_netto | Checkbox | `form.ord_donot_calculate_netto` | Don't calculate netto |
| ord_by_guaranty | Checkbox | `form.ord_by_guaranty` | By guaranty |
| ord_delivery_cost_by | Checkbox | `form.ord_delivery_cost_by` | Delivery cost BY |
| ord_delivery_cost | Input | `form.ord_delivery_cost` | Delivery cost |
| ord_comment | Textarea | `form.ord_comment` | Comment |

### 9. Covering Letter Section
| Field | Type | Source Binding | Notes |
|-------|------|----------------|-------|
| ord_comment_covering_letter | Textarea | `form.ord_comment_covering_letter` | Covering letter text |
| director | ServerList (User) | `form.director` | Director signature |
| logist | ServerList (User) | `form.logist` | Logistician signature |
| director_rb | ServerList (User) | `form.director_rb` | Director RB signature |
| chief_dep | ServerList (User) | `form.chief_dep` | Chief of department |
| manager | ServerList (User) | `form.manager` | Manager |
| ord_logist_signature | Checkbox | `form.ord_logist_signature` | Logist signature flag |
| ord_director_rb_signature | Checkbox | `form.ord_director_rb_signature` | Director RB signature flag |
| ord_chief_dep_signature | Checkbox | `form.ord_chief_dep_signature` | Chief dep signature flag |
| ord_manager_signature | Checkbox | `form.ord_manager_signature` | Manager signature flag |

### 10. Attachments Section
**Grid**: `attachmentsGrid`
**Actions**:
- `deferredAttach` - Add attachment
- `downloadAttachment` - Download (param: `attachmentId`)
- `deleteAttachment` - Delete (param: `attachmentId`)

### 11. Print Parameters Section
| Field | Type | Source Binding | Notes |
|-------|------|----------------|-------|
| ord_print_scale | Select | `form.ord_print_scale` | Print scale % |
| ord_letter_scale | Select | `form.ord_letter_scale` | Letter scale % |
| show_unit | Checkbox | `form.show_unit` | Show units |
| merge_positions | Checkbox | `form.merge_positions` | Merge positions |

## Form Actions (Dispatch Methods)
| Action | Method | Forward | Description |
|--------|--------|---------|-------------|
| input | `input()` | form | Create new order |
| edit | `edit()` | form | Load existing order |
| clone | `clone()` | form | Clone order |
| process | `process()` | back | Save and return to list |
| reload | `reload()` | form | Refresh form |
| print | `print()` | form | Save and print order |
| printLetter | `printLetter()` | form | Save and print covering letter |
| back | `back()` | back | Cancel and return |
| newContractor | `newContractor()` | newContractor | Create new contractor |
| newContactPerson | `newContactPerson()` | newContactPerson | Create new contact person |
| newProduce | `newProduce()` | newProduce | Add produce line |
| editProduce | `editProduce()` | editProduce | Edit produce line |
| cloneProduce | `cloneProduce()` | cloneProduce | Clone produce line |
| deleteProduce | `deleteProduce()` | form | Delete produce line |
| selectCP | `selectCP()` | selectCP | Select from Commercial Proposal |
| importExcel | `importExcel()` | importExcel | Import from Excel |
| uploadTemplate | `uploadTemplate()` | uploadTemplate | Upload template |
| deferredAttach | `deferredAttach()` | deferredAttach | Add attachment |
| deleteAttachment | `deleteAttachment()` | form | Delete attachment |
| downloadAttachment | `downloadAttachment()` | - | Download attachment |

## AJAX Actions
| Action | Method | Parameters | Response |
|--------|--------|------------|----------|
| ajaxOrderPaymentsGrid | `ajaxOrderPaymentsGrid()` | - | HTML fragment |
| ajaxAddToPaymentGrid | `ajaxAddToPaymentGrid()` | - | HTML fragment |
| ajaxRemoveFromPaymentGrid | `ajaxRemoveFromPaymentGrid()` | id | HTML fragment |
| ajaxRecalculatePaymentGrid | `ajaxRecalculatePaymentGrid()` | - | HTML fragment |
| ajaxOrderPaySumsGrid | `ajaxOrderPaySumsGrid()` | - | HTML fragment |
| ajaxAddToPaySumGrid | `ajaxAddToPaySumGrid()` | - | HTML fragment |
| ajaxRemoveFromPaySumsGrid | `ajaxRemoveFromPaySumsGrid()` | id | HTML fragment |
| ajaxRecalcPaySumGrid | `ajaxRecalcPaySumGrid()` | - | HTML fragment |
| ajaxIsContractCopy | `ajaxIsContractCopy()` | contract-id | Text message |
| ajaxIsSpecificationCopy | `ajaxIsSpecificationCopy()` | specification-id | Text message |
| ajaxCheckSave | `ajaxCheckSave()` | - | Text message |

## Role-Based Access Control

### Form Read-Only Conditions
- `formReadOnly = true` when `ord_block = "1"` (blocked document)
- `formReadOnly = true` for `isOnlyUserInLithuania` role

### Section-Level Access
| Section | Roles with Edit Access |
|---------|------------------------|
| Header (above logistic) | Admin, Economist, Logistic, Manager |
| Logistic Section | Admin, Economist, Logistic |
| Sent to Production Date | Admin, Economist, Logistic, Manager |
| Attachments | Admin, Economist, Manager, Logistic |
| Arrive in Lithuania | Admin, Economist, UserInLithuania, Logistic |
| Executed Count Button | Admin, Economist, Logistic, Manager |

### Grid Row Actions
| Action | Condition |
|--------|-----------|
| Edit button | Unblocked OR (blocked AND date_conf_all/ready_for_deliv_date_all empty) |
| Clone button | `readOnlyIfNotLikeManager = false` |
| Delete button | `opr_count_executed = 0` AND `opr_occupied empty` AND not formReadOnly |

## Business Rules (from Action)

### Number Generation
```
Format: {sellerForWho.prefix}-{YYMM}/{gen_num}-{usr_code}
Example: DCL-2602/0001-IVAN
```

### Date Validation Sequence
1. `ord_sent_to_prod_date` must be set before `ord_received_conf_date`
2. `ord_received_conf_date` must be set before `ord_conf_sent_date`
3. `ord_received_conf_date` must be set before `ord_ready_for_deliv_date`
4. `ord_ready_for_deliv_date` must be set before `ord_executed_date`

### Save Validation
1. If `ord_in_one_spec` is set, contractor_for, contract, specification must be filled
2. Date sequence must be valid
3. All produce lines must have produce selected (except admin can save with empty)
4. If `ord_all_include_in_spec` and not `ord_by_guaranty`, drp_price must be > 0
5. If all counts executed and no executed_date and not annulled - warning
6. If `ord_date_conf_all` set, `ord_date_conf` must be filled
7. If `ord_ready_for_deliv_date_all` set, date, shippingDocType, shp_doc_number must be filled

### Block Logic
- Order is blocked (`ord_block = "1"`) when:
  - All counts executed AND executed_date is set
  - OR order is annulled

## Session State
- Order bean stored in session: `StoreUtil.putSession(request, Order.class)`
- Form is session-scoped (struts-config: `scope="session"`)
- Attachments use `DeferredAttachmentService` with transaction commit on save

## Dependent Screens
- **OrderProduce.jsp** - Produce line edit dialog
- **Contractor Edit** - Via `newContractor` forward
- **ContactPerson Edit** - Via `newContactPerson` forward
- **Commercial Proposal Selection** - Via `selectCP` forward
- **Excel Import** - Via `importExcel` forward
