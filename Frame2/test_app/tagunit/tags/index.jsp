<%@ taglib uri="http://www.tagunit.org/tagunit/core" prefix="tagunit" %>

<%--
  Tests for TagUnit tag libraries
  -------------------------------
  This page contains the tests required to automatically test the basics of
  the tag libraries that are a part of the TagUnit framework - warnings are ignored
--%>

<tagunit:testTagLibrary uri="/tagunit/tags">
  <tagunit:tagLibraryDescriptor uri="/WEB-INF/template_taglib.tld" />
</tagunit:testTagLibrary>

<tagunit:testTagLibrary uri="/tagunit/tags">
  <tagunit:tagLibraryDescriptor uri="/WEB-INF/taglib.tld" />
</tagunit:testTagLibrary>