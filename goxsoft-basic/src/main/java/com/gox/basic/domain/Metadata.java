package com.gox.basic.domain;

import cn.hutool.core.util.StrUtil;
import com.gox.common.annotation.Excel;
import com.gox.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * 文书类基本元数据对象 metadata
 *
 * @author gox
 * @date 2020-12-28
 */
@Alias(value = "Metadata")
public class Metadata extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 是否已归 1--已归档 0--未归档
     */
    private transient Long archived;
    /**
     * id
     */
    private Long id;
    /**
     * 部门ID
     **/
    //@Excel(name = "部门id")
    private Long deptId;
    /**
     * 聚合层次
     */
    @Excel(name = "聚合层次")
    private String aggregationLevel;

    /**
     * 档案馆名称
     */
    @Excel(name = "档案馆名称")
    private String archivesName;

    /**
     * 档案馆代码
     */
    @Excel(name = "档案馆代码")
    private String archivesIdentifier;

    /**
     * 全宗名称
     */
    @Excel(name = "全宗名称")
    private String fondsName;

    /**
     * 立档单位名称
     */
    @Excel(name = "立档单位名称")
    private String fondsConstitutingUnitName;

    /**
     * 电子文件号
     */
    @Excel(name = "电子文件号")
    private String electronicRecordCode;

    /**
     * 档号
     */
    @Excel(name = "档号")
    private String archivalCode;

    /**
     * 全宗号
     */
    @Excel(name = "全宗号")
    private String fondsIdentifier;

    /**
     * 目录号
     */
    @Excel(name = "目录号")
    private String catalogueNumber;

    /**
     * 年度
     */
    @Excel(name = "年度")
    private Long year;

    /**
     * 保管期限
     */
    @Excel(name = "保管期限")
    private String retentionPeriod;

    /**
     * 机构或问题
     */
    @Excel(name = "机构或问题")
    private String organizationalStructureOrFunction;

    /**
     * 类别号
     */
    @Excel(name = "类别号")
    private String categoryCode;

    /**
     * 室编案卷号
     */
    @Excel(name = "室编案卷号")
    private String agencyFileNumber;

    /**
     * 馆编案卷号
     */
    @Excel(name = "馆编案卷号")
    private String archivesFileNumber;

    /**
     * 室编件号
     */
    @Excel(name = "室编件号")
    private Long agencyItemNumber;

    /**
     * 馆编件号
     */
    @Excel(name = "馆编件号")
    private Long archivesItemNumber;

    /**
     * 文档序号
     */
    @Excel(name = "文档序号")
    private String documentSequenceNumber;

    /**
     * 页号
     */
    @Excel(name = "页号")
    private String pageNumber;

    /**
     * 内容描述
     */
    @Excel(name = "内容描述")
    private String contentDescription;

    /**
     * 题名
     */
    @Excel(name = "题名")
    private String title;

    /**
     * 并列题名
     */
    @Excel(name = "并列题名")
    private String parallelTitle;

    /**
     * 副题名
     */
    @Excel(name = "副题名")
    private String alternativeTitle;

    /**
     * 说明题名文字
     */
    @Excel(name = "说明题名文字")
    private String otherTitleInformation;

    /**
     * 主题词
     */
    @Excel(name = "主题词")
    private String descriptor;

    /**
     * 关键词
     */
    @Excel(name = "关键词")
    private String keyword;

    /**
     * 人名
     */
    @Excel(name = "人名")
    private String personalName;

    /**
     * 摘要
     */
    @Excel(name = "摘要")
    private String abstracts;

    /**
     * 分类号
     */
    @Excel(name = "分类号")
    private String classCode;

    /**
     * 文件编号
     */
    @Excel(name = "文件编号")
    private String documentNumber;

    /**
     * 责任者
     */
    @Excel(name = "责任者")
    private String author;

    /**
     * 日期
     */
    @Excel(name = "日期")
    private String date;

    /**
     * 文种
     */
    @Excel(name = "文种")
    private String documentType;

    /**
     * 紧急程度
     */
    @Excel(name = "紧急程度")
    private String precedence;

    /**
     * 主送
     */
    @Excel(name = "主送")
    private String principalReceiver;

    /**
     * 抄送
     */
    @Excel(name = "抄送")
    private String otherReceivers;

    /**
     * 密级
     */
    @Excel(name = "密级")
    private String securityClassification;

    /**
     * 保密期限
     */
    @Excel(name = "保密期限")
    private String secrecyPeriod;

    /**
     * 形式特征
     */
    @Excel(name = "形式特征")
    private String formalCharacteristics;

    /**
     * 文件组合类型
     */
    @Excel(name = "文件组合类型")
    private String documentAggregationType;

    /**
     * 件数
     */
    @Excel(name = "件数")
    private Long totalNumberOfItems;

    /**
     * 页数
     */
    @Excel(name = "页数")
    private Long totalNumberOfPages;

    /**
     * 语种
     */
    @Excel(name = "语种")
    private String language;

    /**
     * 稿本
     */
    @Excel(name = "稿本")
    private String manuscriptType;
    /**
     * 排序
     **/
    private String sortField;
    /**
     * 排序策略
     **/
    private String orderBy;
    /**
     * 电子文件
     */
    private List<ElectronicAttributes> electronicAttributes;
    /**
     * 备用字段 1-5 长字段 6-15短字段
     * regex   ^f[1-9][0-9]*$
     */
    private MetadataReserve metadataReserve;
    /**
     * 案卷用 父id
     **/
    private Long parentId;

    private Long nodeId;

    public Metadata() {
    }

    public Metadata(Long deptId, Long parentId, Long nodeId) {
        this.deptId = deptId;
        this.parentId = parentId;
        this.nodeId = nodeId;
    }

    public Long getArchived() {
        return archived;
    }

    public void setArchived(Long archived) {
        this.archived = archived;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public MetadataReserve getMetadataReserve() {
        return metadataReserve;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public void setMetadataReserve(MetadataReserve metadataReserve) {
        this.metadataReserve = metadataReserve;
    }
    /**
     * 电子签名
     */
    private List<DigitalSignature> digitalSignatures;
    /**
     * 权限管理
     */
    private List<RightsManagement> rightsManagements;

    /**
     * 附注
     */
    @Excel(name = "附注")
    private String annotation;

    public List<ElectronicAttributes> getElectronicAttributes() {
        return electronicAttributes;
    }

    public void setElectronicAttributes(List<ElectronicAttributes> electronicAttributes) {
        this.electronicAttributes = electronicAttributes;
    }

    public List<DigitalSignature> getDigitalSignatures() {
        return digitalSignatures;
    }

    public void setDigitalSignatures(List<DigitalSignature> digitalSignatures) {
        this.digitalSignatures = digitalSignatures;
    }

    public List<RightsManagement> getRightsManagements() {
        return rightsManagements;
    }

    public void setRightsManagements(List<RightsManagement> rightsManagements) {
        this.rightsManagements = rightsManagements;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setAggregationLevel(String aggregationLevel) {
        this.aggregationLevel = aggregationLevel;
    }

    public String getAggregationLevel() {
        return aggregationLevel;
    }

    public void setArchivesName(String archivesName) {
        this.archivesName = archivesName;
    }

    public String getArchivesName() {
        return archivesName;
    }

    public void setArchivesIdentifier(String archivesIdentifier) {
        this.archivesIdentifier = archivesIdentifier;
    }

    public String getArchivesIdentifier() {
        return archivesIdentifier;
    }

    public void setFondsName(String fondsName) {
        this.fondsName = fondsName;
    }

    public String getFondsName() {
        return fondsName;
    }

    public void setFondsConstitutingUnitName(String fondsConstitutingUnitName) {
        this.fondsConstitutingUnitName = fondsConstitutingUnitName;
    }

    public String getFondsConstitutingUnitName() {
        return fondsConstitutingUnitName;
    }

    public void setElectronicRecordCode(String electronicRecordCode) {
        this.electronicRecordCode = electronicRecordCode;
    }

    public String getElectronicRecordCode() {
        return electronicRecordCode;
    }

    public void setArchivalCode(String archivalCode) {
        this.archivalCode = archivalCode;
    }

    public String getArchivalCode() {
        return archivalCode;
    }

    public void setFondsIdentifier(String fondsIdentifier) {
        this.fondsIdentifier = fondsIdentifier;
    }

    public String getFondsIdentifier() {
        return fondsIdentifier;
    }

    public void setCatalogueNumber(String catalogueNumber) {
        this.catalogueNumber = catalogueNumber;
    }

    public String getCatalogueNumber() {
        return catalogueNumber;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Long getYear() {
        return year;
    }

    public void setRetentionPeriod(String retentionPeriod) {
        this.retentionPeriod = retentionPeriod;
    }

    public String getRetentionPeriod() {
        return retentionPeriod;
    }

    public void setOrganizationalStructureOrFunction(String organizationalStructureOrFunction) {
        this.organizationalStructureOrFunction = organizationalStructureOrFunction;
    }

    public String getOrganizationalStructureOrFunction() {
        return organizationalStructureOrFunction;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setAgencyFileNumber(String agencyFileNumber) {
        this.agencyFileNumber = agencyFileNumber;
    }

    public String getAgencyFileNumber() {
        return agencyFileNumber;
    }

    public void setArchivesFileNumber(String archivesFileNumber) {
        this.archivesFileNumber = archivesFileNumber;
    }

    public String getArchivesFileNumber() {
        return archivesFileNumber;
    }

    public void setAgencyItemNumber(Long agencyItemNumber) {
        this.agencyItemNumber = agencyItemNumber;
    }

    public Long getAgencyItemNumber() {
        return agencyItemNumber;
    }

    public void setArchivesItemNumber(Long archivesItemNumber) {
        this.archivesItemNumber = archivesItemNumber;
    }

    public Long getArchivesItemNumber() {
        return archivesItemNumber;
    }

    public void setDocumentSequenceNumber(String documentSequenceNumber) {
        this.documentSequenceNumber = documentSequenceNumber;
    }

    public String getDocumentSequenceNumber() {
        return documentSequenceNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setContentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
    }

    public String getContentDescription() {
        return contentDescription;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setParallelTitle(String parallelTitle) {
        this.parallelTitle = parallelTitle;
    }

    public String getParallelTitle() {
        return parallelTitle;
    }

    public void setAlternativeTitle(String alternativeTitle) {
        this.alternativeTitle = alternativeTitle;
    }

    public String getAlternativeTitle() {
        return alternativeTitle;
    }

    public void setOtherTitleInformation(String otherTitleInformation) {
        this.otherTitleInformation = otherTitleInformation;
    }

    public String getOtherTitleInformation() {
        return otherTitleInformation;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setPersonalName(String personalName) {
        this.personalName = personalName;
    }

    public String getPersonalName() {
        return personalName;
    }

    public void setAbstract(String abstracts) {
        this.abstracts = abstracts;
    }

    public String getAbstract() {
        return abstracts;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setPrecedence(String precedence) {
        this.precedence = precedence;
    }

    public String getPrecedence() {
        return precedence;
    }

    public void setPrincipalReceiver(String principalReceiver) {
        this.principalReceiver = principalReceiver;
    }

    public String getPrincipalReceiver() {
        return principalReceiver;
    }

    public void setOtherReceivers(String otherReceivers) {
        this.otherReceivers = otherReceivers;
    }

    public String getOtherReceivers() {
        return otherReceivers;
    }

    public void setSecurityClassification(String securityClassification) {
        this.securityClassification = securityClassification;
    }

    public String getSecurityClassification() {
        return securityClassification;
    }

    public void setSecrecyPeriod(String secrecyPeriod) {
        this.secrecyPeriod = secrecyPeriod;
    }

    public String getSecrecyPeriod() {
        return secrecyPeriod;
    }

    public void setFormalCharacteristics(String formalCharacteristics) {
        this.formalCharacteristics = formalCharacteristics;
    }

    public String getFormalCharacteristics() {
        return formalCharacteristics;
    }

    public void setDocumentAggregationType(String documentAggregationType) {
        this.documentAggregationType = documentAggregationType;
    }

    public String getDocumentAggregationType() {
        return documentAggregationType;
    }

    public void setTotalNumberOfItems(Long totalNumberOfItems) {
        this.totalNumberOfItems = totalNumberOfItems;
    }

    public Long getTotalNumberOfItems() {
        return totalNumberOfItems;
    }

    public void setTotalNumberOfPages(Long totalNumberOfPages) {
        this.totalNumberOfPages = totalNumberOfPages;
    }

    public Long getTotalNumberOfPages() {
        return totalNumberOfPages;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setManuscriptType(String manuscriptType) {
        this.manuscriptType = manuscriptType;
    }

    public String getManuscriptType() {
        return manuscriptType;
    }

    //    public void setIntellectualPropertyStatement(String intellectualPropertyStatement)
//    {
//        this.intellectualPropertyStatement = intellectualPropertyStatement;
//    }
//
//    public String getIntellectualPropertyStatement()
//    {
//        return intellectualPropertyStatement;
//    }
//    public void setAuthorizedAgent(String authorizedAgent)
//    {
//        this.authorizedAgent = authorizedAgent;
//    }
//
//    public String getAuthorizedAgent()
//    {
//        return authorizedAgent;
//    }
//    public void setPermissionAssignment(String permissionAssignment)
//    {
//        this.permissionAssignment = permissionAssignment;
//    }
//
//    public String getPermissionAssignment()
//    {
//        return permissionAssignment;
//    }
//    public void setControlIdentifier(String controlIdentifier)
//    {
//        this.controlIdentifier = controlIdentifier;
//    }
//
//    public String getControlIdentifier()
//    {
//        return controlIdentifier;
//    }
    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getAnnotation() {
        return annotation;
    }
    public String getAbstracts(){
        return abstracts;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("aggregationLevel", getAggregationLevel())
                .append("archivesName", getArchivesName())
                .append("archivesIdentifier", getArchivesIdentifier())
                .append("fondsName", getFondsName())
                .append("fondsConstitutingUnitName", getFondsConstitutingUnitName())
                .append("electronicRecordCode", getElectronicRecordCode())
                .append("archivalCode", getArchivalCode())
                .append("fondsIdentifier", getFondsIdentifier())
                .append("catalogueNumber", getCatalogueNumber())
                .append("year", getYear())
                .append("retentionPeriod", getRetentionPeriod())
                .append("organizationalStructureOrFunction", getOrganizationalStructureOrFunction())
                .append("categoryCode", getCategoryCode())
                .append("agencyFileNumber", getAgencyFileNumber())
                .append("archivesFileNumber", getArchivesFileNumber())
                .append("agencyItemNumber", getAgencyItemNumber())
                .append("archivesItemNumber", getArchivesItemNumber())
                .append("documentSequenceNumber", getDocumentSequenceNumber())
                .append("pageNumber", getPageNumber())
                .append("contentDescription", getContentDescription())
                .append("title", getTitle())
                .append("parallelTitle", getParallelTitle())
                .append("alternativeTitle", getAlternativeTitle())
                .append("otherTitleInformation", getOtherTitleInformation())
                .append("descriptor", getDescriptor())
                .append("keyword", getKeyword())
                .append("personalName", getPersonalName())
                .append("abstract", getAbstract())
                .append("classCode", getClassCode())
                .append("documentNumber", getDocumentNumber())
                .append("author", getAuthor())
                .append("date", getDate())
                .append("documentType", getDocumentType())
                .append("precedence", getPrecedence())
                .append("principalReceiver", getPrincipalReceiver())
                .append("otherReceivers", getOtherReceivers())
                .append("securityClassification", getSecurityClassification())
                .append("secrecyPeriod", getSecrecyPeriod())
                .append("formalCharacteristics", getFormalCharacteristics())
                .append("documentAggregationType", getDocumentAggregationType())
                .append("totalNumberOfItems", getTotalNumberOfItems())
                .append("totalNumberOfPages", getTotalNumberOfPages())
                .append("language", getLanguage())
                .append("manuscriptType", getManuscriptType())
//            .append("intellectualPropertyStatement", getIntellectualPropertyStatement())
//            .append("authorizedAgent", getAuthorizedAgent())
//            .append("permissionAssignment", getPermissionAssignment())
//            .append("controlIdentifier", getControlIdentifier())
                .append("annotation", getAnnotation())
                .toString();
    }

    public static void main(String[] args) {
        String a = "id, aggregation_level, archives_name, archives_identifier,\n" +
                "        fonds_name, fonds_constituting_unit_name, electronic_record_code,\n" +
                "        archival_code, fonds_identifier, catalogue_number, `year`, retention_period,\n" +
                "        organizational_structure_or_function, category_code, agency_file_number,\n" +
                "        archives_file_number, agency_item_number, archives_item_number,\n" +
                "        document_sequence_number, page_number, content_description, title,\n" +
                "        parallel_title, alternative_title, other_title_information,\n" +
                "        `descriptor`, keyword, personal_name, abstract, class_code,\n" +
                "        document_number, author, `date`, document_type, precedence, principal_receiver,\n" +
                "        other_receivers, security_classification, secrecy_period, formal_characteristics,\n" +
                "        document_aggregation_type, total_number_of_items, total_number_of_pages,\n" +
                "        `language`, manuscript_type, annotation,dept_id,node_id,parent_id";
        String b = "#{item.id},#{item.aggregationLevel},#{item.archivesName},#{item.archivesIdentifier},#{item.fondsName},\n" +
                "            #{item.fondsConstitutingUnitName},#{item.electronicRecordCode},#{item.archivalCode},#{item.fondsIdentifier},\n" +
                "            #{item.catalogueNumber},#{item.year},#{item.retentionPeriod},#{item.organizationalStructureOrFunction},#{item.categoryCode},\n" +
                "            #{item.agencyFileNumber},#{item.archivesFileNumber},#{item.agencyItemNumber},#{item.archivesItemNumber},#{item.documentSequenceNumber},\n" +
                "            #{item.pageNumber},#{item.contentDescription},#{item.title},#{item.parallelTitle},#{item.alternativeTitle},#{item.otherTitleInformation},\n" +
                "            #{item.descriptor},#{item.keyword},#{item.personalName},#{item.abstract},#{item.classCode},#{item.documentNumber},#{item.author},\n" +
                "            #{item.date},#{item.documentType},#{item.precedence},#{item.principalReceiver},#{item.otherReceivers},#{item.securityClassification},\n" +
                "            #{item.secrecyPeriod},#{item.formalCharacteristics},#{item.documentAggregationType},#{item.totalNumberOfItems},#{item.totalNumberOfPages},\n" +
                "            #{item.language},\n" +
                "            #{item.manuscriptType},#{item.annotation},#{item.deptId},#{item.nodeId},#{item.parentId}";
        System.out.println(StrUtil.toCamelCase(a).replaceAll(" ","").replaceAll("\n","").split(",").length);
        System.out.println(StrUtil.toCamelCase(b).replaceAll(" ","").replaceAll("\n","").split(",").length);
    }
}
