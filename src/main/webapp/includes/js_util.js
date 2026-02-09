/**
 * User: Aleksandra Shkrobova
 * Date: 7/5/14
 * Time: 2:44 PM
 */
function moveFocusToNextTableRow(input) {
    input.onkeypress = function () {
        var key = event.keyCode;
        if (key == 13) {
            var input = $(Event.element(event));
            var parentTR = Element.up(input, 'tr');
            var parentTD = Element.up(input, 'td');
            var cellIndex = parentTD.cellIndex;

            var nextTR = Element.next(parentTR, 'tr');
            if (!nextTR) return;
            var necessaryTD = Element.down(nextTR, 'td', cellIndex);
            var nextInput = Element.down(necessaryTD, 'input');

            if (nextInput) {
                nextInput.focus();
            }

        } else {
            return;
        }
    }
}