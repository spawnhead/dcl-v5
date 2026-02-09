Date.prototype.getWeek = function ()
{
  //lets calc weeknumber the cruel and hard way :D
  var year = this.getFullYear();
  var month = this.getMonth();
  var day = this.getDate();
  //Find JulianDay

  month += 1; //use 1-12

  var a = Math.floor((14 - (month)) / 12);
  var y = year + 4800 - a;
  var m = (month) + (12 * a) - 3;
  var jd = day + Math.floor(((153 * m) + 2) / 5) +
           (365 * y) + Math.floor(y / 4) - Math.floor(y / 100) +
           Math.floor(y / 400) - 32045;      // (gregorian calendar)

  //now calc weeknumber according to JD

  var d4 = (jd + 31741 - (jd % 7)) % 146097 % 36524 % 1461;
  var L = Math.floor(d4 / 1460);
  var d1 = ((d4 - L) % 365) + L;
  return Math.floor(d1 / 7) + 1;
}

var weekend = [6,7];

var gNow = new Date();
isNav = (navigator.appName.indexOf("Netscape") != -1);
isIE = (navigator.appName.indexOf("Microsoft") != -1);

Calendar.Months = ["Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
  "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"];

// Non-Leap year Month days..
Calendar.DOMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
// Leap year Month days..
Calendar.lDOMonth = [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
Calendar.frame = null;
Calendar.input = null;
Calendar.css = null;

function Calendar(p_month, p_year, p_format)
{
  if ((p_month == null) && (p_year == null))  return;
  if (p_month == null)
  {
    this.gMonthName = null;
    this.gMonth = null;
    this.gYearly = true;
  }
  else
  {
    this.gMonthName = Calendar.get_month(p_month);
    this.gMonth = Number(p_month);
    this.gYearly = false;
  }

  this.gYear = p_year;
  this.gFormat = p_format;
}

Calendar.get_month = Calendar_get_month;
Calendar.get_daysofmonth = Calendar_get_daysofmonth;
Calendar.calc_month_year = Calendar_calc_month_year;

function Calendar_get_month(monthNo)
{
  return Calendar.Months[monthNo];
}

function Calendar_get_daysofmonth(monthNo, p_year)
{
  /*
   Check for leap year ..
   1.Years evenly divisible by four are normally leap years, except for...
   2.Years also evenly divisible by 100 are not leap years, except for...
   3.Years also evenly divisible by 400 are leap years.
   */
  if ((p_year % 4) == 0)
  {
    if ((p_year % 100) == 0 && (p_year % 400) != 0)
      return Calendar.DOMonth[monthNo];

    return Calendar.lDOMonth[monthNo];
  }
  else
    return Calendar.DOMonth[monthNo];
}

function Calendar_calc_month_year(p_Month, p_Year, incr)
{
  /*
   Will return an 1-D array with 1st element being the calculated month
   and second being the calculated year
   after applying the month increment/decrement as specified by 'incr' parameter.
   'incr' will normally have 1/-1 to navigate thru the months.
   */
  var ret_arr = [];

  if (incr == -1)
  {
    // B A C K W A R D
    if (p_Month == 0)
    {
      ret_arr[0] = 11;
      ret_arr[1] = parseInt(p_Year) - 1;
    }
    else
    {
      ret_arr[0] = parseInt(p_Month) - 1;
      ret_arr[1] = parseInt(p_Year);
    }
  }
  else if (incr == 1)
  {
    // F O R W A R D
    if (p_Month == 11)
    {
      ret_arr[0] = 0;
      ret_arr[1] = parseInt(p_Year) + 1;
    }
    else
    {
      ret_arr[0] = parseInt(p_Month) + 1;
      ret_arr[1] = parseInt(p_Year);
    }
  }

  return ret_arr;
}

// This is for compatibility with Navigator 3, we have to create and discard one object before the prototype object exists.
new Calendar();

Calendar.prototype.getMonthlyCalendarCode = function()
{
  var vCode = "";
  var vHeader_Code;
  var vData_Code = "";

  // Begin Table Drawing code here..
  vCode = vCode + "";

  vHeader_Code = this.cal_header();
  vData_Code = this.cal_data();
  vCode = vCode + vHeader_Code + vData_Code;

  vCode = vCode + "</tr>";
  vCode = vCode + "<TR><TD colspan=8 align=center class=calendar-header>" +
          "<A class=calendar-header-link HREF='#' " +
          "onClick='parent.__selectDate(\"\")' >очистить</A>" +
          "</TD></TR>";
  vCode = vCode + "</TABLE>";

  return vCode;
}

Calendar.prototype.show = function()
{
  var vCode = "";

  Calendar.frame.document.open();

  // Setup the page...
  this.wwrite("<html>");
  this.wwrite("<head><title>Calendar</title>");
  this.wwrite("<LINK href='" + Calendar.css + "' type=text/css rel=stylesheet><link rel=stylesheet type='text/css' href='" + Calendar.css + "'>");
  this.wwrite("</head>");

  this.wwrite("<body leftmargin=0 rightmargin=0 topmargin=0 bottommargin=0>");

  // Show navigation buttons
  var prevMMYYYY = Calendar.calc_month_year(this.gMonth, this.gYear, -1);
  var prevMM = prevMMYYYY[0];
  var prevYYYY = prevMMYYYY[1];

  var nextMMYYYY = Calendar.calc_month_year(this.gMonth, this.gYear, 1);
  var nextMM = nextMMYYYY[0];
  var nextYYYY = nextMMYYYY[1];
  this.wwrite("<TABLE WIDTH='100%' BORDER=0 CELLSPACING=1 CELLPADDING=0 id=__calendarTbl><TR>");
  if (Calendar.onlyHeader == 'false')
  {
    this.wwrite("<TD ALIGN=center class=calendar-header rowspan=2>N</TD>");
  }
  this.wwrite("<TD ALIGN=center class=calendar-header>");
  this.wwrite("<A class=calendar-header-link HREF=\"" +
              "javascript:parent.Build('" + this.gMonth + "', '" + (parseInt(this.gYear) - 1) + "', '" + this.gFormat + "'" +
              ");" +
              "\"><<<\/A></TD><TD ALIGN=center class=calendar-header id=leftMonth>");
  this.wwrite("<A class=calendar-header-link HREF=\"" +
              "javascript:parent.Build('" + prevMM + "', '" + prevYYYY + "', '" + this.gFormat + "'" +
              ");" +
              "\"><<\/A></TD><TD ALIGN=center class=calendar-header colspan=3>");
  this.wwrite("<A class=calendar-header-link HREF=\"" +
              "javascript:parent.__selectMonth('" + this.format_data(1) + "', '" + this.format_data(Calendar.get_daysofmonth(this.gMonth, this.gYear)) + "');" +
              "\">" + this.gMonthName + " " + this.gYear + "<\/A></TD><TD ALIGN=center class=calendar-header>");
  this.wwrite("<A class=calendar-header-link HREF=\"" +
              "javascript:parent.Build('" + nextMM + "', '" + nextYYYY + "', '" + this.gFormat + "'" +
              ");" +
              "\">><\/A></TD><TD ALIGN=center class=calendar-header>");
  this.wwrite("<A class=calendar-header-link HREF=\"" +
              "javascript:parent.Build('" + this.gMonth + "', '" + (parseInt(this.gYear) + 1) + "', '" + this.gFormat + "'" +
              ");" +
              "\">>><\/A></TD></TR>");

  // Get the complete calendar code for the month..
  if (Calendar.onlyHeader == 'false')
  {
    vCode = this.getMonthlyCalendarCode();
  }
  this.wwrite(vCode);

  this.wwrite("</body></html>");
  Calendar.frame.document.close();
  var frame = Calendar.frameObj;

  var calendarTable = Calendar.frame.document.getElementById('__calendarTbl');
  if (calendarTable)
  {
    frame.style.height = calendarTable.offsetHeight + 'px';
  }
}

Calendar.prototype.wwrite = function(wtext)
{
  Calendar.frame.document.writeln(wtext);
}

Calendar.prototype.wwriteA = function(wtext)
{
  Calendar.frame.document.writeln(wtext);
}

Calendar.prototype.cal_header = function()
{
  var vCode = "";

  vCode = vCode + "<TR>";
  vCode = vCode + "<TD WIDTH='13%' class=calendar-days>Пн</TD>";
  vCode = vCode + "<TD WIDTH='13%' class=calendar-days>Вт</TD>";
  vCode = vCode + "<TD WIDTH='13%' class=calendar-days>Ср</TD>";
  vCode = vCode + "<TD WIDTH='13%' class=calendar-days>Чт</TD>";
  vCode = vCode + "<TD WIDTH='13%' class=calendar-days>Пт</TD>";
  vCode = vCode + "<TD WIDTH='13%' class=calendar-days>Суб</TD>";
  vCode = vCode + "<TD WIDTH='13%' class=calendar-days>Вс</TD>";
  vCode = vCode + "</TR>";

  return vCode;
}

Calendar.prototype.cal_data = function()
{
  var vDate = new Date();
  vDate.setDate(1);
  vDate.setMonth(this.gMonth);
  vDate.setFullYear(this.gYear);

  var vFirstDay = vDate.getDay();
  if (vFirstDay == 0)
  {
    vFirstDay = 7;
  }
  var vDay = 1;
  var vLastDay = Calendar.get_daysofmonth(this.gMonth, this.gYear);
  var vOnLastDay = 0;
  var vCode = "";

  /*
   Get day for the 1st of the requested month/year..
   Place as many blank cells before the 1st day of the month as necessary.
   */
  //debugger
  vCode = vCode +
          "<TR><TD class='calendar-weeks'>" +
          "<A HREF='#' " +
          "onClick='parent.__selectMonth(\"" + this.calc_date_by_week_number(vDate.getWeek(), 'begin') + "\", \"" + this.calc_date_by_week_number(vDate.getWeek(), 'end') + "\")' >" +
          vDate.getWeek() +
          "</A>" +
          "</TD>";

  for (var i = 1; i < vFirstDay; i++)
  {
    vCode = vCode + "<TD WIDTH='13%'" + this.write_weekend_string(i) + "></TD>";
  }

  // Write rest of the 1st week
  for (var j = vFirstDay; j < 8; j++)
  {
    vCode = vCode + "<TD WIDTH='13%'  " + this.write_weekend_string(j) + ">" +
            "<A HREF='#' " +
            "onClick='parent.__selectDate(\"" + this.format_data(vDay) + "\")' >" +
            this.format_day(vDay) +
            "</A>" +
            "</TD>";
    vDay = vDay + 1;
  }
  vCode = vCode + "</TR>";

  // Write the rest of the weeks
  for (var k = 2; k < 7; k++)
  {
    vDate.setDate(vDay);
    vCode = vCode +
            "<TR><TD class='calendar-weeks'>" +
            "<A HREF='#' " +
            "onClick='parent.__selectMonth(\"" + this.calc_date_by_week_number(vDate.getWeek(), 'begin') + "\", \"" + this.calc_date_by_week_number(vDate.getWeek(), 'end') + "\")' >" +
            vDate.getWeek() +
            "</A>" +
            "</TD>";

    for (j = 1; j < 8; j++)
    {
      vCode = vCode + "<TD WIDTH='13%' " + this.write_weekend_string(j) + ">" +
              "<A HREF='#' " +
              "onClick='parent.__selectDate(\"" + this.format_data(vDay) + "\")' >" +
              this.format_day(vDay) +
              "</A>" +
              "</TD>";
      vDay = vDay + 1;

      if (vDay > vLastDay)
      {
        vOnLastDay = 1;
        break;
      }
    }

    if (j == 7)
      vCode = vCode + "</TR>";
    if (vOnLastDay == 1)
      break;
  }

  // Fill up the rest of last week with proper blanks, so that we get proper square blocks
  for (var m = 1; m < (8 - j); m++)
  {
    if (this.gYearly)
      vCode = vCode + "<TD WIDTH='13%'" + this.write_weekend_string(j + m) + "></TD>";
    else
      vCode = vCode + "<TD WIDTH='13%'" + this.write_weekend_string(j + m) + " style='color:gray' >" + m + "</TD>";
  }

  return vCode;
}

Calendar.prototype.format_day = function(vday)
{
  var vNowDay = gNow.getDate();
  var vNowMonth = gNow.getMonth();
  var vNowYear = gNow.getFullYear();

  if (vday == vNowDay && this.gMonth == vNowMonth && this.gYear == vNowYear)
    return ("<FONT COLOR=\"RED\">" + vday + "</FONT>");
  else
    return (vday);
}

Calendar.prototype.write_weekend_string = function(vday)
{
  var i;

  // Return special formatting for the weekend day.
  for (i = 0; i < weekend.length; i++)
  {
    if (vday == weekend[i])
      return (" class=calendar-weekend ");
  }

  return " class=calendar-day ";
},

Calendar.prototype.format_data = function(p_day, shiftYear)
{
  var vData;
  var month = parseInt(this.gMonth);
  if ( -1 == shiftYear )
  {
    month = 11;
  }
  if ( 1 == shiftYear )
  {
    month = 0;
  }

  var vMonth = 1 + month;
  vMonth = (vMonth.toString().length < 2) ? "0" + vMonth : vMonth;
  var vMon = Calendar.get_month(month).substr(0, 3).toUpperCase();
  var vFMon = Calendar.get_month(month).toUpperCase();
  var vTMon = Calendar.get_month(month);
  if ( null == shiftYear )
  {
    shiftYear = 0;
  }
  var year = parseInt(this.gYear) + shiftYear;
  var yearStr = year.toString();
  var vY4 = new String(yearStr);
  var vY2 = new String(yearStr.substr(2, 2));
  var vDD = (p_day.toString().length < 2) ? "0" + p_day : p_day;

  switch (this.gFormat)
          {
    case "MM\/DD\/YYYY" :
      vData = vMonth + "\/" + vDD + "\/" + vY4;
      break;
    case "MM\/DD\/YY" :
      vData = vMonth + "\/" + vDD + "\/" + vY2;
      break;
    case "MM-DD-YYYY" :
      vData = vMonth + "-" + vDD + "-" + vY4;
      break;
    case "MM-DD-YY" :
      vData = vMonth + "-" + vDD + "-" + vY2;
      break;

    case "DD\/MON\/YYYY" :
      vData = vDD + "\/" + vMon + "\/" + vY4;
      break;
    case "DD\/MON\/YY" :
      vData = vDD + "\/" + vMon + "\/" + vY2;
      break;
    case "DD-MON-YYYY" :
      vData = vDD + "-" + vMon + "-" + vY4;
      break;
    case "DD-MON-YY" :
      vData = vDD + "-" + vMon + "-" + vY2;
      break;

    case "DD\/MONTH\/YYYY" :
      vData = vDD + "\/" + vFMon + "\/" + vY4;
      break;
    case "DD\/MONTH\/YY" :
      vData = vDD + "\/" + vFMon + "\/" + vY2;
      break;
    case "DD-MONTH-YYYY" :
      vData = vDD + "-" + vFMon + "-" + vY4;
      break;
    case "DD-MONTH-YY" :
      vData = vDD + "-" + vFMon + "-" + vY2;
      break;

    case "DD\/MM\/YYYY" :
      vData = vDD + "\/" + vMonth + "\/" + vY4;
      break;
    case "DD\/MM\/YY" :
      vData = vDD + "\/" + vMonth + "\/" + vY2;
      break;
    case "DD-MM-YYYY" :
      vData = vDD + "-" + vMonth + "-" + vY4;
      break;
    case "DD-MM-YY" :
      vData = vDD + "-" + vMonth + "-" + vY2;
      break;
    case "DD.MM.YYYY" :
      vData = vDD + "." + vMonth + "." + vY4;
      break;
    case "MMMM YYYY" :
      vData = vTMon + " " + vY4;
      break;
    case "DD.MM.YY" :
      vData = vDD + "." + vMonth + "." + vY2;
      break;

    default :
      vData = vMonth + "\/" + vDD + "\/" + vY4;
  }

  return vData;
}

Calendar.prototype.calc_date_by_week_number = function(week, bound)
{
  var vDate = new Date();
  vDate.setFullYear(this.gYear);
  numOfdaysPastSinceLastMonday = eval(vDate.getDay() - 1);
  vDate.setDate(vDate.getDate() - numOfdaysPastSinceLastMonday);
  var weekNoToday = vDate.getWeek();
  var weeksInTheFuture = eval( week - weekNoToday );
  vDate.setDate(vDate.getDate() + eval( 7 * weeksInTheFuture ));
  if ( 'end' == bound )
  {
    vDate.setDate(vDate.getDate() + 6);
  }

  var shiftYear = 0;
  //начало года
  if ( this.gYear > vDate.getFullYear() )
  {
    shiftYear = -1;
  }
  //конец года
  if ( this.gYear < vDate.getFullYear() )
  {
    shiftYear = 1;
  }

  return this.format_data(vDate.getDate(), shiftYear);
}

function Build(p_month, p_year, p_format)
{
  gCal = new Calendar(p_month, p_year, p_format);
  gCal.show();
}

function ShowMonth(obj)
{
  if (!Calendar.frame || !Calendar.frame.document)
  {
    return;
  }
  var leftMonth = Calendar.frame.document.getElementById('leftMonth');
  if (leftMonth)
  {
    document.getElementById('monthFrame').style.left = getAbsPosX(Calendar.input) + getAbsPosX(leftMonth) + 2;
    document.getElementById('monthFrame').style.top = getAbsPosY(Calendar.input) + getAbsPosY(leftMonth) + Calendar.input.offsetHeight + leftMonth.offsetHeight + 1;
  }
  document.getElementById('monthFrame').style.display = 'block';
}

function HideMonth()
{
  document.getElementById('monthFrame').style.display = 'none';
}

function __getElementByIdOrName(name)
{
  return document.getElementById(name) || document.getElementsByName(name)[0];
}

function __showCalendar(id, input, btnStr, css, afterSelectCallback, startInput, endInput, onlyHeader, params, evt)
{
  __documentMouseClick();

  var btn = document.getElementById(btnStr);
  var inp = __getElementByIdOrName(input);
  var frameObj = document.getElementById(id);
  if (!btn || !inp || !frameObj)
  {
    return false;
  }

  Calendar.css = css;
  Calendar.input = inp;
  if (startInput != '')
  {
    Calendar.startInput = __getElementByIdOrName(startInput);
    Calendar.endInput = __getElementByIdOrName(endInput);
  }
  Calendar.frameObj = frameObj;
  Calendar.frame = Calendar.frameObj.contentWindow || window.frames[id] || null;
  Calendar.afterSelectCallback = afterSelectCallback;
  Calendar.onlyHeader = onlyHeader;
  Calendar.params = params;

  Calendar.frameObj.style.height = 100;
  Calendar.frameObj.style.display = "";

  var selectedDate = Calendar.input.value;
  var month = String(gNow.getMonth());
  var year = String(gNow.getFullYear().toString());
  if ( selectedDate != '' )
  {
    var segmentsDate = selectedDate.split(".");
    if ( segmentsDate.length > 1 && segmentsDate[1] != '' )
    {
      if ( segmentsDate[1].charAt(0) == '0' )
      {
        month = String(parseInt(segmentsDate[1].charAt(1)) - 1);
      }
      else
      {
        month = String(parseInt(segmentsDate[1]) - 1);
      }
    }
    if ( segmentsDate.length > 2 && segmentsDate[2] != '' )
    {
      year = segmentsDate[2];  
    }
  }
  if (Calendar.onlyHeader == 'false')
  {
    Build(month, year, "DD.MM.YYYY");
  }
  if (Calendar.onlyHeader == 'true')
  {
    Build(month, year, "MMMM YYYY");
  }
  __savedDocumentClickHandler = document.onclick;
  document.onclick = __documentMouseClick;
  __listObject = Calendar.frameObj;
  var eventObj = evt || window.event;
  if (eventObj && eventObj.stopPropagation)
  {
    eventObj.stopPropagation();
  }
  else if (eventObj)
  {
    eventObj.cancelBubble = true;
  }

  Calendar.frameObj.style.left = getAbsPosX(inp);
  Calendar.frameObj.style.top = getAbsPosY(inp) + inp.offsetHeight - 1;
  Calendar.frameObj.style.width = getAbsPosX(btn) + btn.offsetWidth - getAbsPosX(inp) - 2;

  return false;
}

var __calSavedDocumentClickHandler = null;
function __calDocumentMouseClick()
{
  if (__calObject && event.srcElement != __calObject)
  {
    document.onclick = __calSavedDocumentClickHandler;
    __calSavedDocumentClickHandler = null;
    __calObject.style.display = "none";
    __calObject = null;
    return true;
  }
  return true;
}

function __selectDate(vDay)
{
  Calendar.input.value = vDay;
  Calendar.frameObj.style.display = 'none';
  document.onclick = null;
  var callback = Calendar.afterSelectCallback;
  Calendar.afterSelectCallback = null;
  if (callback != null)
  {
    var arg = {value:vDay,params:Calendar.params};
    callback.call(null, arg);
  }
}

function __selectMonth(vDay1, vDay2)
{
  if (Calendar.startInput != undefined)
  {
    Calendar.startInput.value = vDay1;
    Calendar.endInput.value = vDay2;
  }
  else
  {
    Calendar.input.value = vDay1;
  }
  Calendar.frameObj.style.display = 'none';
  document.onclick = null;
  var callback = Calendar.afterSelectCallback;
  Calendar.afterSelectCallback = null;
  if (callback != null)
  {
    var arg = {value:Calendar.input.value,params:Calendar.params};
    callback.call(null, arg);
  }
}
