package com.zf.classfile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2017/11/27.
 * Controller代码行生成
 */
public class JPAControllerGen {


    public static List<String> gen( String urlResource,String entityName, String entityPackageName, String controllerPackageName,
                                   String servicePackageName,  String author,String annonationRemark,String formPackageName) {
        List<String> lineList = new ArrayList<>();

        List<String> headerList = genHeader(urlResource,entityName, entityPackageName, controllerPackageName, author,annonationRemark,servicePackageName,formPackageName);

        List<String> bodyList = genBody(entityName,annonationRemark);

        lineList.addAll(headerList);
        lineList.addAll(bodyList);
        lineList.add("}");
        return lineList;
    }


    /**
     * 生成头部
     *
     * @param entityName
     * @param entityPackageName
     * @param controllerPackageName
     * @param author
     */
    private static List<String> genHeader(String urlResource,String entityName, String entityPackageName, String controllerPackageName,  String author,
                                          String annonationRemark,String servicePackageName,String formPackageName) {
        List<String> headerList = new ArrayList<>();
        headerList.add("package " + controllerPackageName + ";");
        headerList.add(" ");

        headerList.add("import atg.taglib.json.util.JSONArray;");
        headerList.add("import " + formPackageName + "." + entityName + "Form;");
        headerList.add("import "+entityPackageName+"."+entityName+";");
        headerList.add("import "+servicePackageName+"." + entityName + "Service;");
        headerList.add("import com.szdxit.sws.core.common.exception.ServiceException;");
        headerList.add("import com.szdxit.sws.core.mvc.AbstractAnnotationController;");
        headerList.add("import com.szdxit.sws.core.mvc.ResultObj;");
        headerList.add("import com.szdxit.sws.rbac.annoation.ApiAction;");
        headerList.add("import com.szdxit.sws.rbac.annoation.ApiController;");
        headerList.add("import com.szdxit.sws.rbac.annoation.ApiLevel;");
        headerList.add("import org.springframework.beans.BeanUtils;");
        headerList.add("import org.springframework.beans.factory.annotation.Autowired;");
        headerList.add("import org.springframework.util.CollectionUtils;");
        headerList.add("import org.springframework.web.bind.annotation. *;");
        headerList.add("import java.util. *;");
        headerList.add("  ");
        headerList.add("/**");
        headerList.add("* Created by " + author + " on " + new SimpleDateFormat("YYYY/MM/dd").format(new Date()) + ".");
        headerList.add("*/");
        headerList.add("@RestController");
        headerList.add("@RequestMapping(\""+urlResource+"\")");
        headerList.add("@ApiController(\""+annonationRemark+"模块\")");
        headerList.add(" public class "+entityName+"Controller extends AbstractAnnotationController {");
        headerList.add("    @Autowired");
        headerList.add("    private " + entityName + "Service " + ClassFileUtil.toLowCaseFirst(entityName) + "Service;");
        headerList.add("  ");

        return headerList;
    }

    /**
     * 生成类体
     *
     * @param entityName
     * @param annonationRemark
     * @return
     */
    private static List<String> genBody(String entityName,String annonationRemark) {
        List<String> bodyList = new ArrayList<>();
        String objName = ClassFileUtil.toLowCaseFirst(entityName);
        String tab = "    ";
        String dbTab = tab + tab;
        // 全部列表
        bodyList.add(tab + "@ApiAction(name = \"全部"+annonationRemark+"列表\", level = ApiLevel.GRANT_REQUIRED)");
        bodyList.add(tab + "@RequestMapping(value = \"/listall\", method = RequestMethod.GET)");
        bodyList.add(tab + "public ResultObj<Collection<" + entityName + ">> listAll() {");
        bodyList.add(dbTab + "return new ResultObj<>(" + objName + "Service.listAll());");
        bodyList.add(tab + "}");
        bodyList.add("  ");
        // 分页查询列表
        bodyList.add("    @ApiAction(name = \""+annonationRemark+"列表\", level = ApiLevel.GRANT_REQUIRED)");
        bodyList.add("    @RequestMapping(method = RequestMethod.GET)");
        bodyList.add("    public ResultObj<Map<String, Object>> list(@RequestParam(name = \"filterBy\", required = false) String filterBy, @RequestParam(name = \"orderBy\", required = false) String orderBy,");
        bodyList.add("            @RequestParam(name = \"pageIndex\", required = false) Integer pageIndex, @RequestParam(name = \"pageSize\", required = false) Integer pageSize) {");
        bodyList.add("       return new ResultObj(this." + objName + "Service.list(filterBy, orderBy, pageIndex, pageSize));");
        bodyList.add("    }");
        bodyList.add("  ");

        //get
        bodyList.add("     @ApiAction(name = \""+annonationRemark+"明细\", level = ApiLevel.GRANT_REQUIRED)");
        bodyList.add("     @RequestMapping(value = \"/{id:\\\\d+}\", method = RequestMethod.GET)");
        bodyList.add("     public ResultObj<"+entityName+"> get(@PathVariable(\"id\") int id) {");
        bodyList.add("         return new ResultObj<>(" + objName + "Service.get(id));");
        bodyList.add("     }");
        bodyList.add("  ");

        // add
        bodyList.add("    @ApiAction(name = \"新增"+annonationRemark+"\", level = ApiLevel.GRANT_REQUIRED)");
        bodyList.add("    @RequestMapping(method = {RequestMethod.POST})");
        bodyList.add("    public ResultObj<Integer> add(@RequestBody " + entityName + "Form " + objName + "Form) {");
        bodyList.add("        try {");
        bodyList.add("            return new ResultObj(" + objName + "Service.add(" + objName + "Form));");
        bodyList.add("        } catch (ServiceException e) {");
        bodyList.add("            return new ResultObj(ResultObj.FAILURE, e.getMessage());");
        bodyList.add("        }");
        bodyList.add("    }");
        bodyList.add("  ");

        // edit
        bodyList.add("    @ApiAction(name = \"编辑"+annonationRemark+"\", level = ApiLevel.GRANT_REQUIRED)");
        bodyList.add("    @RequestMapping(value = \"/{id:\\\\d+}\", method = {RequestMethod.PUT})");
        bodyList.add("    public ResultObj<Integer> update(@PathVariable(\"id\") int id, @RequestBody " + entityName + "Form " + objName + "Form) {");
        bodyList.add("        try {");
        bodyList.add("            " + objName + "Service.edit(id, " + objName + "Form);");
        bodyList.add("        } catch (ServiceException e) {");
        bodyList.add("           return new ResultObj(ResultObj.FAILURE, e.getMessage());");
        bodyList.add("        }");
        bodyList.add("        return new ResultObj();");
        bodyList.add("    }");
        bodyList.add("  ");

        // delete
        bodyList.add("    @ApiAction(name = \"删除"+annonationRemark+"\", level = ApiLevel.GRANT_REQUIRED)");
        bodyList.add("    @RequestMapping(value = \"/{id:\\\\d+}\", method = RequestMethod.DELETE)");
        bodyList.add("    public ResultObj<Integer> delete(@PathVariable(\"id\") int id) {");
        bodyList.add("        try {");
        bodyList.add("            "+objName+"Service.delete(id);");
        bodyList.add("        }catch (ServiceException e){");
        bodyList.add("           return new ResultObj(ResultObj.FAILURE,e.getMessage());");
        bodyList.add("       }");
        bodyList.add("       return new ResultObj();");
        bodyList.add("    }");
        bodyList.add("  ");

        return bodyList;
    }

}
