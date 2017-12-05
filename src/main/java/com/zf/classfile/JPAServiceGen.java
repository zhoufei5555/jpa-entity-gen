package com.zf.classfile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhoufei on 2017/11/27.
 * 生成Service类代码
 */
public class JPAServiceGen {

    public static List<String> gen(List<Column> columnList,String entityName, String entityPackageName,String servicePackageName,
                                   String repositoryPackageName, String author,String annonationRemark,String formPackageName) {
        List<String> lineList = new ArrayList<>();
        List<String> headerList = genHeader(servicePackageName, repositoryPackageName, entityName, entityPackageName, author,formPackageName);
        List<String> bodyList = genBody(columnList,entityName,annonationRemark);

        lineList.addAll(headerList);
        lineList.addAll(bodyList);
        lineList.add("}");
        return lineList;
    }

    /**
     * 生成service头部信息
     *
     * @param servicePackageName
     * @param repositoryPackageName
     * @param entityName
     * @param entityPackageName
     * @param author
     * @return
     */
    private static List<String> genHeader(String servicePackageName, String repositoryPackageName,
                                          String entityName, String entityPackageName,  String author,String formPackageName) {
        List<String> headerList = new ArrayList<>();
        headerList.add("package " + servicePackageName + ";");
        headerList.add(" ");
        headerList.add("import atg.taglib.json.util.JSONArray;");
        headerList.add("import atg.taglib.json.util.JSONException;");
        headerList.add("import atg.taglib.json.util.JSONObject;");
        headerList.add("import "+formPackageName+"."+entityName+"Form;");
        headerList.add("import " + entityPackageName + "." + entityName + ";");
        headerList.add("import " + repositoryPackageName + "." + entityName + "Repository;");
        headerList.add("import com.szdxit.sws.core.common.exception.ServiceException;");
        headerList.add("import com.szdxit.sws.core.common.relation.RelationKey;");
        headerList.add("import com.szdxit.sws.core.jpa.service.AbstractEntityService;");
        headerList.add("import com.szdxit.sws.rbac.annoation.ApiRelation;");
        headerList.add("import org.springframework.beans.factory.annotation.Autowired;");
        headerList.add("import org.springframework.data.domain.Page;");
        headerList.add("import org.springframework.data.domain.Pageable;");
        headerList.add("import org.springframework.data.jpa.domain.Specification;");
        headerList.add("import org.springframework.stereotype.Service;");
        headerList.add("import org.springframework.util.StringUtils;");
        headerList.add("import javax.transaction.Transactional;");
        headerList.add("import java.sql.Timestamp;");
        headerList.add("import java.util.HashMap;");
        headerList.add("import java.util.List;");
        headerList.add("import java.util.Map;");
        headerList.add("");
        headerList.add("/**");
        headerList.add("* Created by " + author + " on " + new SimpleDateFormat("YYYY/MM/dd").format(new Date()) + ".");
        headerList.add("*/");
        headerList.add("@Service");
        headerList.add("public class " + entityName + "Service extends AbstractEntityService<" + entityName + "> {");
        headerList.add("");
        headerList.add("    @Autowired");
        headerList.add("    private " + entityName + "Repository " + ClassFileUtil.toLowCaseFirst(entityName) + "Repository;");
        headerList.add("");
        return headerList;
    }

    /**
     * 生成service体部信息
     * @param columnList
     * @param entityName
     * @param annonationRemark
     * @return
     */
    private static List<String> genBody(List<Column> columnList, String entityName,String annonationRemark) {
        List<String> bodyList = new ArrayList<>();

        String objName = ClassFileUtil.toLowCaseFirst(entityName);
        String tab = "    ";
        String dbTab = tab+tab;
        // 生成list All
        bodyList.add(tab+"  public List<" + entityName + "> listAll() {  return " + objName + "Repository.findAll(); }");
        // 生成 list
        bodyList.add(tab+"public Map<String, Object> list(String filterBy, String orderBy, Integer pageIndex, Integer pageSize) {");
        bodyList.add(dbTab+"// 字段名称映射 - 兼容关联字段排序用");
        bodyList.add(dbTab+"Map<String, Object> resultMap = new HashMap<>();");
        bodyList.add("      ");
        bodyList.add(dbTab+"Map<String, String> paramKeyMap = new HashMap<>();");
        bodyList.add("      ");
        bodyList.add(dbTab+"// 分页排序信息 ");
        bodyList.add(dbTab+"Pageable pageable = createPageable(pageIndex, pageSize, orderBy, paramKeyMap);");
        bodyList.add(dbTab+"Specification<"+entityName+"> specification = createSpecification(getFilter(filterBy), paramKeyMap);");
        bodyList.add(dbTab+"Page<"+entityName+"> meterPage = this."+objName+"Repository.findAll(specification, pageable);");
        bodyList.add(dbTab+"resultMap.put(RESULT_DATA, meterPage.getContent());");
        bodyList.add(dbTab+" resultMap.put(RESULT_RECORDSFILTERED, meterPage.getTotalElements());");
        bodyList.add(dbTab+"resultMap.put(RESULT_RECORDSTOTAL, meterPage.getTotalElements());");
        bodyList.add(dbTab+" return resultMap;");
        bodyList.add(tab+"}");
        bodyList.add("  ");
        // 生成 add
        bodyList.add(tab+"@Transactional");
        bodyList.add(tab+"public int add("+entityName+"Form "+objName+"Form) {");
        bodyList.add(dbTab+entityName+" "+objName+" = new "+entityName+"();");
        bodyList.add(dbTab+"checkbaseInfo("+objName+"Form);");
        bodyList.add(dbTab+"buildObj("+objName+","+objName+"Form);");
        bodyList.add("      ");
        bodyList.add(dbTab+"return this."+objName+"Repository.save("+objName+").getId();");
        bodyList.add(tab+"}");
        bodyList.add("      ");

        // 生成 edit
        bodyList.add(tab+"@Transactional");
        bodyList.add(tab+"public void edit(int id, "+entityName+"Form "+objName+"Form) {");
        bodyList.add(dbTab+entityName+" "+objName+" = this."+objName+"Repository.getOne(id);");
        bodyList.add(dbTab+"buildObj("+objName+","+objName+"Form);");
        bodyList.add("      ");
        bodyList.add(dbTab+"this.update("+objName+");");
        bodyList.add(dbTab+"}");
        bodyList.add("      ");
        // 生成delete
        bodyList.add(tab+"@Transactional");
        bodyList.add(tab+"public void delete(int id) {this."+objName+"Repository.delete(id);}");
        bodyList.add("      ");
        // 生成Relation
        bodyList.add(tab+"@ApiRelation(code = \""+objName+"s\", name = \""+annonationRemark+"\")");
        bodyList.add(tab+"public JSONArray listForRelation() {");
        bodyList.add(dbTab+"List<"+entityName+"> "+objName+"s = this."+objName+"Repository.findAll();");
        bodyList.add(dbTab+"JSONArray jsonArray = new JSONArray();");
        bodyList.add(dbTab+"try {");
        bodyList.add(dbTab+tab+"for ("+entityName+" "+objName+" : "+objName+"s) {");
        bodyList.add(dbTab+dbTab+"JSONObject jsonObject = new JSONObject();");
        bodyList.add(dbTab+dbTab+"jsonObject.put(RelationKey.VALUE, "+objName+".getId());");
        bodyList.add(dbTab+dbTab+"jsonObject.put(RelationKey.LABEL, "+objName+".getName());");
        bodyList.add(dbTab+dbTab+"jsonArray.add(jsonObject);");
        bodyList.add(dbTab+tab+"}");
        bodyList.add(dbTab+"} catch (JSONException e) {");
        bodyList.add(dbTab+tab+"e.printStackTrace();");
        bodyList.add(dbTab+"}");
        bodyList.add(dbTab+tab+"return jsonArray;");
        bodyList.add(dbTab+"}");
        bodyList.add("      ");

        // 生成checkbaseinfo
        bodyList.add(tab+"private void checkbaseInfo("+entityName+"Form "+objName+"Form) {");
        bodyList.add(tab+"}");

        // 生成buildObj
        bodyList.add(tab+"private void buildObj("+entityName+" "+objName+", "+entityName+"Form form) {");
        List<String> excludeList = new ArrayList<>();
        excludeList.add("id");
        excludeList.add("create_time");
        excludeList.add("update_time");
        excludeList.add("create_user_id");
        excludeList.add("modify_user_id");
        excludeList.add("update_user_id");
        for(Column column:columnList){
            if(excludeList.contains(column.getName())){
                continue;
            }
            String upFirstName = ClassFileUtil.sqlNameToJavaName(column.getName(),ClassFileUtil.UP_FIRST);
            bodyList.add(dbTab+"if(!StringUtils.isEmpty(form.get"+upFirstName+"())){");
            bodyList.add(dbTab+tab+objName+".set"+upFirstName+"(form.get"+upFirstName+"());");
            bodyList.add(dbTab+"}");
        }
        bodyList.add(tab+"}");

        return bodyList;
    }

}


