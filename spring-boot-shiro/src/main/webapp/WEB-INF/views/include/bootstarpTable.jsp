<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/bootstrapTable/dist/bootstrap-table.css" rel="stylesheet" />
<script src="${ctxStatic}/bootstrapTable/dist/bootstrap-table.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrapTable/dist/locale/bootstrap-table-zh-CN.js" type="text/javascript"></script>
<style>
/* Base class */
.bs-form-title {
  position: relative;
  margin: 15px 0;
  padding: 39px 19px 14px;
  *padding-top: 19px;
  background-color: #fff;
  border: 1px solid #ddd;
  -webkit-border-radius: 4px;
     -moz-border-radius: 4px;
          border-radius: 4px;
}

/* Echo out a label for the example */
.bs-form-title:after {
  content: "查询";
  position: absolute;
  top: -1px;
  left: -1px;
  padding: 3px 7px;
  font-size: 12px;
  font-weight: bold;
  background-color: #f5f5f5;
  border: 1px solid #ddd;
  color: #9da0a4;
  -webkit-border-radius: 4px 0 4px 0;
  -moz-border-radius: 4px 0 4px 0;
  border-radius: 4px 0 4px 0;
}

.bs-form-title + .prettyprint {
    margin-top: -20px;
    padding-top: 15px;
}

.prettyprint {
  padding: 8px;
  background-color: #f7f7f9;
  border: 1px solid #e1e1e8;
}


</style>