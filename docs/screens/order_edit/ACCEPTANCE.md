# Order Edit Screen - ACCEPTANCE CRITERIA

## Overview
This document defines acceptance criteria for the Order Edit screen migration.
All criteria must pass for the screen to be considered "parity complete".

---

## AC-001: Screen Entry

### AC-001.1: Create New Order
**Given**: User is on Orders list screen
**When**: User clicks "New" button
**Then**:
- [ ] Order edit form opens with empty fields
- [ ] `ord_date` is set to current date
- [ ] `ord_number` is empty (will be generated on save)
- [ ] `director` is pre-filled from config
- [ ] `logist` is pre-filled from config
- [ ] `director_rb` is pre-filled from config
- [ ] `manager` is set to current user
- [ ] `currency` is set to default (EUR)
- [ ] Payment grid has one row with 100%
- [ ] Pay sums grid has one empty row
- [ ] Form is editable (not blocked)

### AC-001.2: Edit Existing Order
**Given**: User is on Orders list screen with existing orders
**When**: User clicks on a row
**Then**:
- [ ] Order edit form opens with all fields populated
- [ ] `ord_number` is displayed (readonly)
- [ ] Produces grid shows all line items
- [ ] Payments grid shows saved payments
- [ ] Pay sums grid shows saved sums
- [ ] Attachments are listed
- [ ] Block status is respected (readonly if blocked)

### AC-001.3: Clone Order
**Given**: User is on Orders list screen
**When**: User clicks "Clone" on a row
**Then**:
- [ ] Order edit form opens with copied data
- [ ] `ord_id` is empty
- [ ] `ord_number` is empty (will be generated)
- [ ] `ord_date` is set to current date
- [ ] All logistic dates are cleared
- [ ] `ord_block` is cleared
- [ ] `ord_annul` is cleared
- [ ] Produces are copied
- [ ] Form is editable

---

## AC-002: Header Fields

### AC-002.1: Seller Selection
**Given**: Order edit form is open
**When**: User selects a seller from "Seller" dropdown
**Then**:
- [ ] Seller prefix is stored for order number generation
- [ ] Seller details are available for print template

### AC-002.2: Contractor Selection
**Given**: Order edit form is open
**When**: User selects a contractor
**Then**:
- [ ] Contractor is stored in form
- [ ] Contact person list is filtered by contractor
- [ ] Contact person field is cleared if different contractor

### AC-002.3: Contact Person Selection
**Given**: Contractor is selected
**When**: User selects a contact person
**Then**:
- [ ] Contact person is stored in form
- [ ] Contact person belongs to selected contractor

### AC-002.4: Blank Selection
**Given**: Order edit form is open
**When**: User selects a blank (print template)
**Then**:
- [ ] Blank is stored for print generation
- [ ] Blank image name is updated

---

## AC-003: Produces Grid

### AC-003.1: Add Produce Line
**Given**: Order edit form is open
**When**: User clicks "Add" button in produces section
**Then**:
- [ ] OrderProduce dialog opens
- [ ] New line is added after dialog save
- [ ] Line number is auto-assigned
- [ ] Totals are recalculated

### AC-003.2: Edit Produce Line
**Given**: Order has produce lines
**When**: User clicks edit button on a line
**Then**:
- [ ] OrderProduce dialog opens with line data
- [ ] Changes are saved after dialog save
- [ ] Totals are recalculated

### AC-003.3: Clone Produce Line
**Given**: Order has produce lines
**When**: User clicks clone button on a line
**Then**:
- [ ] OrderProduce dialog opens with copied data
- [ ] New line is added after dialog save
- [ ] Line number is auto-assigned

### AC-003.4: Delete Produce Line
**Given**: Order has produce lines with no executed count
**When**: User clicks delete button on a line
**Then**:
- [ ] Line is removed from grid
- [ ] Line numbers are re-sequenced
- [ ] Totals are recalculated

### AC-003.5: Delete Produce Line - Blocked
**Given**: Order has produce lines with executed count > 0
**When**: User attempts to delete the line
**Then**:
- [ ] Delete button is disabled or hidden
- [ ] Line cannot be deleted

### AC-003.6: Import from Commercial Proposal
**Given**: Order edit form is open
**When**: User clicks "Select CP" button and selects a CP
**Then**:
- [ ] CP produce lines are imported
- [ ] Contractor is set from CP
- [ ] Currency is set from CP
- [ ] Warning is shown if CP has blocked positions

### AC-003.7: Import from Excel
**Given**: Order edit form is open
**When**: User clicks "Import Excel" and uploads file
**Then**:
- [ ] Excel data is parsed
- [ ] Produce lines are created from Excel rows
- [ ] Validation errors are shown for invalid rows

---

## AC-004: Payments Grid (AJAX)

### AC-004.1: Display Payments Grid
**Given**: Order edit form is open
**When**: Payments section loads
**Then**:
- [ ] Grid shows all payment rows
- [ ] Percent, Sum, Date, Description columns are visible
- [ ] Description is calculated from percent/sum

### AC-004.2: Add Payment Row
**Given**: Payments grid is displayed
**When**: User clicks add button
**Then**:
- [ ] New row is added with empty fields
- [ ] Grid is refreshed via AJAX
- [ ] No page reload

### AC-004.3: Remove Payment Row
**Given**: Payments grid has multiple rows
**When**: User clicks remove on a row
**Then**:
- [ ] Row is removed
- [ ] If only one row remains, it gets 100%
- [ ] Grid is refreshed via AJAX

### AC-004.4: Recalculate Payments
**Given**: Payments grid has rows
**When**: User changes percent or sum
**Then**:
- [ ] Description is recalculated
- [ ] Grid is refreshed via AJAX

---

## AC-005: Pay Sums Grid (AJAX)

### AC-005.1: Display Pay Sums Grid
**Given**: Order edit form is open
**When**: Pay sums section loads
**Then**:
- [ ] Grid shows all pay sum rows
- [ ] Sum and Date columns are visible

### AC-005.2: Add Pay Sum Row
**Given**: Pay sums grid is displayed
**When**: User clicks add button
**Then**:
- [ ] New row is added with empty fields
- [ ] Grid is refreshed via AJAX

### AC-005.3: Remove Pay Sum Row
**Given**: Pay sums grid has multiple rows
**When**: User clicks remove on a row
**Then**:
- [ ] Row is removed
- [ ] If only one row remains, it gets total sum
- [ ] Grid is refreshed via AJAX

---

## AC-006: Logistic Section

### AC-006.1: Date Sequence Validation
**Given**: Order edit form is open
**When**: User fills logistic dates
**Then**:
- [ ] `ord_received_conf_date` must be >= `ord_sent_to_prod_date`
- [ ] `ord_conf_sent_date` must be >= `ord_received_conf_date`
- [ ] `ord_ready_for_deliv_date` must be >= `ord_received_conf_date`
- [ ] `ord_executed_date` must be >= `ord_ready_for_deliv_date`
- [ ] Error shown if sequence is violated

### AC-006.2: Date Conf All Checkbox
**Given**: `ord_date_conf_all` is checked
**When**: Form is saved
**Then**:
- [ ] `ord_date_conf` must be filled
- [ ] Error shown if empty

### AC-006.3: Ready for Delivery All Checkbox
**Given**: `ord_ready_for_deliv_date_all` is checked
**When**: Form is saved
**Then**:
- [ ] `ord_ready_for_deliv_date` must be filled
- [ ] `shippingDocType` must be selected
- [ ] `ord_shp_doc_number` must be filled
- [ ] Error shown if any is empty

### AC-006.4: Clear Ready for Delivery All
**Given**: `ord_ready_for_deliv_date_all` is unchecked
**When**: Form reloads
**Then**:
- [ ] `ord_ready_for_deliv_date` is cleared
- [ ] `shippingDocType` is cleared
- [ ] `ord_shp_doc_number` is cleared
- [ ] `ord_ship_from_stock` is cleared
- [ ] `ord_arrive_in_lithuania` is cleared
- [ ] All produce ready-for-shipping records are cleared

---

## AC-007: Additional Options

### AC-007.1: In One Spec Checkbox
**Given**: `ord_in_one_spec` is checked
**When**: Form is saved
**Then**:
- [ ] `contractor_for` must be selected
- [ ] `contract` must be selected
- [ ] `specification` must be selected
- [ ] Error shown if any is empty

### AC-007.2: Discount All
**Given**: `ord_discount_all` is checked
**When**: User enters discount
**Then**:
- [ ] Discount is applied to all lines
- [ ] Totals are recalculated

### AC-007.3: Include NDS
**Given**: `ord_include_nds` is checked
**When**: User enters NDS rate
**Then**:
- [ ] NDS is included in calculations
- [ ] Totals are recalculated

---

## AC-008: Save Operations

### AC-008.1: Save New Order
**Given**: New order form is filled with valid data
**When**: User clicks "Save" button
**Then**:
- [ ] Order number is generated: `{prefix}-{YYMM}/{num}-{code}`
- [ ] Order is saved to database
- [ ] User is redirected to Orders list
- [ ] Success message is shown

### AC-008.2: Save Existing Order
**Given**: Existing order form is modified
**When**: User clicks "Save" button
**Then**:
- [ ] Order is updated in database
- [ ] User is redirected to Orders list
- [ ] Success message is shown

### AC-008.3: Save with Empty Produces
**Given**: Order has no produce lines
**When**: User clicks "Save" button
**Then**:
- [ ] Warning message is shown via AJAX
- [ ] Save can proceed (warning only)

### AC-008.4: Save with Null Produce
**Given**: Order has produce line without product selected
**When**: User clicks "Save" button
**Then**:
- [ ] Error message is shown (non-admin)
- [ ] Admin can save with empty produce (parent doc only)

### AC-008.5: Save with DRP Price Check
**Given**: `ord_all_include_in_spec` is checked
**When**: DRP price / course / netto price > 1.5
**Then**:
- [ ] Warning message is shown via AJAX

---

## AC-009: Block Logic

### AC-009.1: Auto-Block on Execution
**Given**: All produce counts are executed
**When**: `ord_executed_date` is set
**Then**:
- [ ] `ord_block` is set to "1"
- [ ] Form becomes readonly

### AC-009.2: Auto-Block on Annul
**Given**: Order is not blocked
**When**: `ord_annul` is checked and saved
**Then**:
- [ ] `ord_block` is set to "1"
- [ ] Form becomes readonly

### AC-009.3: Unblock Not Allowed
**Given**: Order is blocked
**When**: User views the form
**Then**:
- [ ] All fields are readonly
- [ ] Only print buttons are available

---

## AC-010: Print Operations

### AC-010.1: Print Order
**Given**: Order form is valid
**When**: User clicks "Print" button
**Then**:
- [ ] Order is saved (if new)
- [ ] Print scale is saved
- [ ] PDF is generated
- [ ] PDF opens in new window/tab

### AC-010.2: Print Covering Letter
**Given**: Order form is valid
**When**: User clicks "Print Letter" button
**Then**:
- [ ] Order is saved (if new)
- [ ] Letter scale is saved
- [ ] PDF is generated
- [ ] PDF opens in new window/tab

---

## AC-011: Role-Based Access

### AC-011.1: Manager Role
**Given**: User has "Manager" role
**When**: Viewing unblocked order
**Then**:
- [ ] Can edit header section
- [ ] Can edit produces grid
- [ ] Can edit sent_to_prod_date
- [ ] Cannot edit logistic section (except sent_to_prod)
- [ ] Can add attachments

### AC-011.2: Logistic Role
**Given**: User has "Logistic" role
**When**: Viewing unblocked order
**Then**:
- [ ] Can edit header section
- [ ] Can edit logistic section
- [ ] Can add attachments
- [ ] Can edit Arrive in Lithuania

### AC-011.3: User in Lithuania Role
**Given**: User has "UserInLithuania" role only
**When**: Viewing order
**Then**:
- [ ] Form is readonly
- [ ] Can only print
- [ ] Can edit "Arrive in Lithuania" field

### AC-011.4: Admin Role
**Given**: User has "Admin" role
**When**: Viewing order
**Then**:
- [ ] Can edit all sections
- [ ] Can save with empty produce (parent doc only)
- [ ] Can see admin-only fields

---

## AC-012: Attachments

### AC-012.1: Add Attachment
**Given**: Order edit form is open
**When**: User clicks "Add Attachment" and selects file
**Then**:
- [ ] File is uploaded
- [ ] Attachment appears in grid
- [ ] Attachment is stored temporarily (deferred)

### AC-012.2: Download Attachment
**Given**: Order has attachments
**When**: User clicks download on attachment
**Then**:
- [ ] File downloads to browser
- [ ] Correct filename is used

### AC-012.3: Delete Attachment
**Given**: Order has attachments
**When**: User clicks delete on attachment
**Then**:
- [ ] Attachment is removed from grid
- [ ] Attachment is marked for deletion

### AC-012.4: Commit Attachments on Save
**Given**: Order has pending attachments
**When**: User saves order
**Then**:
- [ ] Attachments are committed to database
- [ ] Attachments are linked to order

### AC-012.5: Rollback Attachments on Cancel
**Given**: Order has pending attachments
**When**: User clicks "Cancel" button
**Then**:
- [ ] Attachments are rolled back
- [ ] Temporary files are deleted

---

## AC-013: Cancel Operation

### AC-013.1: Cancel New Order
**Given**: New order form has changes
**When**: User clicks "Cancel" button
**Then**:
- [ ] Order is not saved
- [ ] Attachments are rolled back
- [ ] User is redirected to Orders list

### AC-013.2: Cancel Edit Order
**Given**: Existing order form has changes
**When**: User clicks "Cancel" button
**Then**:
- [ ] Changes are discarded
- [ ] Attachments are rolled back
- [ ] User is redirected to Orders list

---

## AC-014: Validation Messages

| Message Key | Condition |
|-------------|-----------|
| `error.order.ord_in_one_spec` | In-one-spec checked but contractor_for/contract/spec missing |
| `error.order.queue_date` | Date sequence violated |
| `error.order.ord_received_conf_date` | received_conf before sent_to_prod |
| `error.order.ord_conf_sent_date` | conf_sent before received_conf |
| `error.order.ord_ready_for_deliv_date` | ready_for_deliv before received_conf |
| `error.order.ord_executed_date` | executed before ready_for_deliv |
| `error.order.ord_null_produce` | Produce line without product (non-admin) |
| `error.order.null_drp_price` | DRP price is 0 when all_include_in_spec |
| `error.order.not_entered_executed_date` | All executed but no executed_date |
| `error.order.not_entered_date_conf` | date_conf_all but no date_conf |
| `error.order.not_entered_ready_for_deliv_fields` | ready_for_deliv_all but missing fields |
| `error.order.ordExecutedDateInLine` | executed_date before line executed dates |
| `msg.order.empty_table` | No produce lines (warning) |
| `msg.order_produce.check_dlr_price` | DRP price coefficient > 1.5 |

---

## Acceptance Sign-Off

| Role | Name | Date | Status |
|------|------|------|--------|
| BA | | | [ ] |
| QA | | | [ ] |
| Dev | | | [ ] |
| Product Owner | | | [ ] |
