/**
 * Filename    : Array.js
 * Author      : Robert Cerny
 * Created     : 2006-07-26
 * Last Change : 2007-05-17
 *
 * Description:
 *   Some useful methods for the Array prototype.
 *
 * History:
 *   2007-05-17 Using short names for method and signature.
 *   2007-05-09 Modified append to accept null and undefined.
 *   2007-05-03 Refactored.
 *   2007-03-15 Added signatures.
 *   2007-01-06 Added interception.
 *   2006-12-09 Fixed bug in contains.
 *   2006-12-08 Fixed bug in append.
 *   2006-07-26 Created.
 */

CERNY.require("CERNY.js.Array");

(function() {

    var method = CERNY.method;
    var signature = CERNY.signature;

    CERNY.js.Array = {};

    Array.prototype.logger = CERNY.Logger("CERNY.js.Array");

    /**
     * Map an array onto another array based on func.
     *
     * func - the function that should be applied to all elements of this
     *        array
     * return - an array of the results of the applications of func to the
     *          items of this array
     */
    function map(func) {
        var result = new Array(this.length);
        for (var i = 0; i < this.length; i++) {
            result[i] = func(this[i]);
        }
        return result;
    };
    signature(map, Array, "function");
    method(Array.prototype, "map", map);

    /**
     * Append array to this array.
     *
     * array - the array to append
     */
    function append(array) {
        if (array) {
            for (var i = 0; i < array.length; i += 1) {
                this.push(array[i]);
            }
        }
    };
    signature(append, "undefined", ["null", "undefined", Array]);
    method(Array.prototype, "append", append);

    if (!Array.prototype.push) {
        Array.prototype.push = function() {
            for (var i = 0; i < arguments.length; i++) {
                this[this.length] = arguments[i];
            }
            return this.length;
        };
    }

    /**
     * Filter this array through predicate.
     *
     * predicate - the predicate to apply to all items of array
     * return - a new array containing all items that return true on application of predicate
     */
    function filter(predicate) {
        var result = [];
        for (var i = 0; i < this.length; i += 1) {
            if (predicate(this[i])){
                result.push(this[i]);
            }
        }
        return result;
    };
    signature(filter, Array, "function");
    method(Array.prototype, "filter", filter);

    /**
     * Copy this array.
     *
     * return - a copy of this array
     */
    function copy() {
        return this.filter(function() {return true; });
    };
    signature(copy, Array);
    method(Array.prototype, "copy", copy);

    /**
     * Figure out the index of item in array based on cmpFunc.
     *
     * item - the item to look for in this array
     * cmpFunc - the function to use to decide on identity
     * return - the index of the position of item in this array or -1, if
     *          item is not in this array.
     */
    function indexOf(item, cmpFunc) {
        if (!isFunction(cmpFunc)) {
            cmpFunc = function (a, b) { return a == b; };
        }
        for (var i = 0; i < this.length; i++) {
            if (cmpFunc(this[i], item)) return i;
        }
        return -1;
    };
    signature(indexOf, "number", "any", ["undefined", "function"]);
    method(Array.prototype, "indexOf", indexOf);

    /**
     * Figure out whether item is contained in this array.
     *
     * item - the item to look for in this array
     * cmpFunc - the function to use to decide on identity
     * return - true if x is in this array, false otherwise
     */
    function contains(item, cmpFunc) {
        var i = this.indexOf(item, cmpFunc);
        if (i >= 0 ) {
            return true;
        }
        return false;
    }
    signature(contains, "boolean", "any", "function");
    method(Array.prototype, "contains", contains);

    /**
     * Remove item from this array.
     *
     * item - the item to remove
     * cmpFunc - the function to use to decide on identity
     */
    function remove(item, cmpFunc) {
        var i = this.indexOf(item, cmpFunc);
        if (i >= 0 ) {
            this.splice(i,1);
        }
    };
    signature(remove, "undefined", "any", ["undefined", "function"]);
    method(Array.prototype, "remove", remove);

    /**
     * Replace an item in an array.
     *
     * replaced - the item to be replaced
     * replacing - the item to take the position of the replaced
     * cmpFunc - the function to use to decide on identity
     */
    function replace(replaced, replacing, cmpFunc) {
        var i = this.indexOf(replaced, cmpFunc);
        if (i < 0) {
            this.push(replacing);
        } else {
            this.splice(i, 1, replacing);
        }
    };
    signature(replace, "undefined", "any", "any", ["undefined", "function"]);
    method(Array.prototype, "replace", replace);

    /**
     * Figure out whether array is a sub array of this array based on
     * cmpFunc.
     *
     * array - the array that is checked whether it's a sub array
     * cmpFunc - the function to use to decide on identity
     * return - true, if there is no item in array that is not in this
     *          array, false otherwise
     */
    function isSubArray(array, cmpFunc) {
        for (var i = 0; i < this.length; i++) {
            if (array.indexOf(this[i], cmpFunc) < 0) {
                return false;
            }
        }
        return true;
    };
    signature(isSubArray, "boolean", Array, ["undefined", "function"]);
    method(Array.prototype, "isSubArray", isSubArray);

}) ();
