<%--
 Created with IntelliJ IDEA.
 User: Aleksandra Shkrobova
 Date: 10/30/13
--%>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>
<script type="text/javascript" src="includes/plupload-2.1.2/js/plupload.full.min.js"></script>
<script type="text/javascript">
    window.onload = function () {
        var uploader = new plupload.Uploader({
            runtimes: 'silverlight',
            browse_button: 'pickfiles',
            url: getUploadServletUrl(),
            silverlight_xap_url: 'includes/plupload-2.1.2/js/Moxie.xap',
            init: {
                PostInit: function () {
                    document.getElementById('filelist').innerHTML = '';

                    document.getElementById('uploadfiles').onclick = function () {
                        uploader.start();
                        return false;
                    };
                },

                FilesAdded: function (up, files) {
                    plupload.each(files, function (file) {
                        document.getElementById('filelist').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ') <b></b></div>';
                    });
                },

                UploadProgress: function (up, file) {
                    document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
                },

                Error: function (up, err) {
                    console.log("\nError #" + err.code + ": " + err.message);
                }
            }
        });
        uploader.init();
        uploader.refresh();
    }

    function getUploadServletUrl() {
      return '/dcl/MultipleFileUploadHelper.do;jsessionid=' + '${MultipleFileUpload.sessionId}' + '?method=upload';
    }
</script>
<ctrl:form enctype="multipart/form-data">
  <table class=formBack align="center">
    <tr>
      <td colspan="4" align="right">
        <div id="filelist" style="text-align: center">Your browser doesn't have Silverlight support.</div>

        <div id="container">
          <button id="pickfiles" type="button" class="width120"><bean:message key="link.select.files"/></button>
          <button id="uploadfiles" type="button" class="width120"><bean:message key="link.upload.files"/></button>
          <ctrl:ubutton type="link" dispatch="back" styleClass="width120" readonly="false">
            <bean:message key="button.cancel"/>
          </ctrl:ubutton>
          <ctrl:ubutton type="submit" dispatch="save" styleClass="width120" readonly="false">
            <bean:message key="button.save"/>
          </ctrl:ubutton>
        </div>
      </td>
    </tr>
  </table>

</ctrl:form>
