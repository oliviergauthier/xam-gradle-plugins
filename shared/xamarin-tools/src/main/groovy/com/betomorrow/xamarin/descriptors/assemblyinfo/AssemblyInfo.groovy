package com.betomorrow.xamarin.descriptors.assemblyinfo

class AssemblyInfo {

    public static final String AssemblyTitle = "AssemblyTitle"
    public static final String AssemblyDescription = "AssemblyDescription"
    public static final String AssemblyConfiguration = "AssemblyConfiguration"
    public static final String AssemblyCompany = "AssemblyCompany"
    public static final String AssemblyProduct = "AssemblyProduct"
    public static final String AssemblyCopyright = "AssemblyCopyright"
    public static final String AssemblyTrademark = "AssemblyTrademark"
    public static final String AssemblyCulture = "AssemblyCulture"
    public static final String AssemblyVersion = "AssemblyVersion"

    private String assemblyInfoPath

    private Map<String, String> data

    public String version

    AssemblyInfo(String path, Map<String, String> data) {
        this.assemblyInfoPath = path
        this.data = data
    }

    String getPath() {
        return assemblyInfoPath
    }

    Map<String, String> getData() {
        return data
    }

    String getValue(String key, String defaultValue) {
        if (data.containsKey(key)) {
            return data[key]
        }
        return defaultValue
    }

    String getTitle() {
        return data[AssemblyTitle]
    }

    void setTitle(String title) {
        data[AssemblyTitle] = title
    }

    String getDescription() {
        return data[AssemblyDescription]
    }

    void setDescription(String description) {
        data[AssemblyDescription] = description
    }

    String getConfiguration() {
        return data[AssemblyConfiguration]
    }

    void setConfiguration(String configuration) {
        data[AssemblyConfiguration] = configuration
    }

    String getCompany() {
        return data[AssemblyCompany]
    }

    void setCompany(String company) {
        data[AssemblyCompany] = company
    }

    String getProduct() {
        return data[AssemblyProduct]
    }

    void setProduct(String product) {
        data[AssemblyProduct] = product
    }

    String getCopyright() {
        return data[AssemblyCopyright]
    }

    void setCopyright(String copyright) {
        data[AssemblyCopyright] = copyright
    }

    String getTrademark() {
        return data[AssemblyTrademark]
    }

    void setTrademark(String trademark) {
        data[AssemblyTrademark] = trademark
    }

    String getCulture() {
        return data[AssemblyCulture]
    }

    void setCulture(String culture) {
        data[AssemblyCulture] = culture
    }

    String getVersion() {
        return data[AssemblyVersion]
    }

    void setVersion(String version) {
        data[AssemblyVersion] = version
    }

}
