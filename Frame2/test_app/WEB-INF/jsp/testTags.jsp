<%@ taglib uri="/WEB-INF/taglib.tld" prefix="frame2" %>

<frame2:html xhtml="${testTags.xhtml}" lang="en">
  <frame2:head>
    <TITLE>Test Tags Page</TITLE>
	<SCRIPT LANGUAGE="JavaScript1.1">
		function setText() {
			testingTags.textTag.value="WILL YOU JUST STOP CLICKING!!!!!";    
		}
	</SCRIPT>        
  </frame2:head>
  <frame2:body>
    <frame2:errors/>
    <frame2:form name="testingTags" method="post" action="testTags.f2">
      <TABLE>
        <TR>
        </TR>
       <TR>
          <TD><b>html/xhtml Radio:</b>
          </td>
          <TD>
            <frame2:radio name="htmlXhtmlRadioTag" checked="${testTags.htmlXhtmlRadioTag}" displayvalue="html" value="htmlRadio"/>
          </TD>
          <TD>
            <frame2:submit value="Reload" name="reload button"/>
          </TD>
        </TR>
       <TR>
          <TD><b></b>
          </td>
          <TD>
            <frame2:radio name="htmlXhtmlRadioTag" checked="${testTags.htmlXhtmlRadioTag}" displayvalue="xhtml" value="xhtmlRadio"/>
          </TD>
        </TR>
        <TR>
          <TD><b>Text Tag:</b>
          </td>
          <TD>
            <frame2:text name="textTag" size="40" value="${testTags.textTag}"/>
          </TD>
        </TR>
        <TR>
          <TD><b>Button Tag:</b>
          </td>
          <TD>
            <frame2:button name="buttonTag" value="Stop Clicking !!!!" onclick="javascript:setText()"/>
          </TD>
        </TR>
        <TR>
          <TD><b>Password Tag:</b>
          </td>
          <TD>
            <frame2:password name="passwordTag" size="40" value="${testTags.passwordTag}"/>
          </TD>
        </TR>
       <TR>
          <TD><b>Radio checked Tag:</b>
          </td>
          <TD>
            <frame2:radio name="radioTag" checked="${testTags.checkedValue}" displayvalue="me" value="me"/>
          </TD>
        </TR>
       <TR>
          <TD><b>Radio unchecked Tag:</b>
          </td>
          <TD>
            <frame2:radio name="radioTag" checked="${testTags.checkedValue}" displayvalue="you" value="you"/>
          </TD>
        </TR>
       <TR>
          <TD><b>Checkbox checked Tag:</b>
          </td>
          <TD>
            <frame2:checkbox name="checkboxTag" checked="${testTags.checkedValue}" displayvalue="me" value="me"/>
          </TD>
        </TR>
       <TR>
          <TD><b>Checkbox unchecked Tag:</b>
          </td>
          <TD>
            <frame2:checkbox name="checkboxTag" checked="${testTags.checkedValue}" displayvalue="you" value="you"/>
          </TD>
        </TR>
       <TR>
          <TD><b>checkbox Multi Box Tag:</b>
          </td>
          <TD>
           <frame2:checkbox name="radioMulti" checked="${testTags.list}" displayvalue="one" value="one"/> 
          </TD>
          <TD>
           <frame2:checkbox name="radioMulti" checked="${testTags.list}" displayvalue="two" value="two"/> 
          </TD>
          <TD>
           <frame2:checkbox name="radioMulti" checked="${testTags.list}" displayvalue="not checked" value="three"/> 
          </TD>
          <TD>
           <frame2:checkbox name="radioMulti" checked="${testTags.list}" displayvalue="four" value="four"/> 
          </TD>
        </TR>
       <TR>
          <TD><b>TextArea Tag:</b>
          </td>
          <TD>
            <frame2:textarea name="textareaTag" rows="10" cols="10" value="${testTags.textareaTag}" />
          </TD>
        </TR>
       <TR>
          <TD><b>Empty TextArea Tag:</b>
          </td>
          <TD>
            <frame2:textarea name="textareaTag" rows="10" cols="10" />
          </TD>
        </TR>
        <TR>
          <TD>&nbsp;</TD>
        </TR>
       <TR>
          <TD><b>Select/OptionTag:</b>
          </TD>
          <TD>
            <frame2:select name="selectTag" selected="${testTags.checkedValue}">
               <frame2:option value="not me" displayvalue="not me"/>
               <frame2:option value="me" displayvalue="me"/>               
            </frame2:select>
          </TD>
        </TR>
       <TR>
          <TD><b>Select/Option MultipleTag:</b>
          </TD>
          <TD>
            <frame2:select name="selectTag" selected="${testTags.checkedValue}" multiple="true">
               <frame2:option value="me" displayvalue="me"/>
               <frame2:option value="not me" displayvalue="not me"/>
            </frame2:select>
          </TD>
        </TR>
      <TR>
          <TD><b>Select/Options Tag:</b>
          </TD>
          <TD>
            <frame2:select name="selectTag" selected="${testTags.selectedList}">
               <frame2:options value="${testTags.list}" displayvalue="${testTags.list}"/>            
            </frame2:select>
          </TD>
        </TR> 
       <TR>
      <TR>
          <TD><b>Select/Options MultipleTag:</b>
          </TD>
          <TD>
            <frame2:select name="selectTag" selected="${testTags.selectedList}" multiple="true">
               <frame2:options value="${testTags.list}" displayvalue="${testTags.list}"/>            
            </frame2:select>
          </TD>
        </TR> 
       <TR>
          <TD><b>A Tag:</b>
          </td>
          <TD>
            <frame2:a href="www.redwingssuck.com" name="wings">Red Wings Suck!</frame2:a> 
          </TD>
        </TR>
       <TR>
          <TD><b>A Tag w/ nested image:</b>
          </td>
          <TD>
            <frame2:a href="www.redwingssuck.com" name="wings"><frame2:img src="java.gif" alt="The Java Logo (courtesy Sun Microsystems)"/></frame2:a> 
          </TD>
        </TR>
       <TR>
          <TD><b>Reset Label Tag:</b>
          </td>
          <TD>
            <frame2:reset value="Reset With Label" />
          </TD>
        </TR>   
       <TR>
          <TD><b>Reset Tag:</b>
          </td>
          <TD>
            <frame2:reset />
          </TD>
        </TR>           
        <TR>
          <TD>&nbsp;</TD>
        </TR>
        <TR>
          <TD><b>Cancel Tag:</b>
          </td>
          <TD>
            <frame2:cancel value="Cancel Schmancel"/>
          </TD>
        </TR>
        <TR>
          <TD><b>Submit Tag:</b>
          </td>
          <TD>
            <frame2:submit value="Submit!" name="stupid button"/>
          </TD>
        </TR>
        <TR>
          <TD><b>Image (input) Tag:</b>
          </td>
          <TD>
            <frame2:image src="java.gif" alt="The Java Logo (courtesy Sun Microsystems)"/>
          </TD>
        </TR>
      <TD><b><A HREF="displayUsers.f2">Display Users</A></b></TD>
   </TR>
      </TABLE>
    </frame2:form>

    <b>Plain old img tag: </b>    
    <frame2:img src="java.gif" alt="The Java Logo (courtesy Sun Microsystems)"/>
    
  </frame2:body>
</frame2:html>
