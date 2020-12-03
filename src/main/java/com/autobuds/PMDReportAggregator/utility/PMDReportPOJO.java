
package com.autobuds.PMDReportAggregator.utility;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "formatVersion",
    "pmdVersion",
    "timestamp",
    "files",
    "suppressedViolations",
    "processingErrors",
    "configurationErrors"
})
public class PMDReportPOJO implements Serializable
{

    @JsonProperty("formatVersion")
    private Integer formatVersion;
    @JsonProperty("pmdVersion")
    private String pmdVersion;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("files")
    private List<ApexFile> files = null;
    @JsonProperty("suppressedViolations")
    private List<Object> suppressedViolations = null;
    @JsonProperty("processingErrors")
    private List<String> processingErrors = null;
    @JsonProperty("configurationErrors")
    private List<Object> configurationErrors = null;
    private final static long serialVersionUID = 6526670921323421215L;

    @JsonProperty("formatVersion")
    public Integer getFormatVersion() {
        return formatVersion;
    }

    @JsonProperty("formatVersion")
    public void setFormatVersion(Integer formatVersion) {
        this.formatVersion = formatVersion;
    }

    @JsonProperty("pmdVersion")
    public String getPmdVersion() {
        return pmdVersion;
    }

    @JsonProperty("pmdVersion")
    public void setPmdVersion(String pmdVersion) {
        this.pmdVersion = pmdVersion;
    }

    @JsonProperty("timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty("files")
    public List<ApexFile> getFiles() {
        return files;
    }

    @JsonProperty("files")
    public void setFiles(List<ApexFile> files) {
        this.files = files;
    }

    @JsonProperty("suppressedViolations")
    public List<Object> getSuppressedViolations() {
        return suppressedViolations;
    }

    @JsonProperty("suppressedViolations")
    public void setSuppressedViolations(List<Object> suppressedViolations) {
        this.suppressedViolations = suppressedViolations;
    }

    @JsonProperty("processingErrors")
    public List<String> getProcessingErrors() {
        return processingErrors;
    }

    @JsonProperty("processingErrors")
    public void setProcessingErrors(List<String> processingErrors) {
        this.processingErrors = processingErrors;
    }

    @JsonProperty("configurationErrors")
    public List<Object> getConfigurationErrors() {
        return configurationErrors;
    }

    @JsonProperty("configurationErrors")
    public void setConfigurationErrors(List<Object> configurationErrors) {
        this.configurationErrors = configurationErrors;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("formatVersion", formatVersion).append("pmdVersion", pmdVersion).append("timestamp", timestamp).append("files", files).append("suppressedViolations", suppressedViolations).append("processingErrors", processingErrors).append("configurationErrors", configurationErrors).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(suppressedViolations).append(configurationErrors).append(processingErrors).append(pmdVersion).append(files).append(formatVersion).append(timestamp).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PMDReportPOJO) == false) {
            return false;
        }
        PMDReportPOJO rhs = ((PMDReportPOJO) other);
        return new EqualsBuilder().append(suppressedViolations, rhs.suppressedViolations).append(configurationErrors, rhs.configurationErrors).append(processingErrors, rhs.processingErrors).append(pmdVersion, rhs.pmdVersion).append(files, rhs.files).append(formatVersion, rhs.formatVersion).append(timestamp, rhs.timestamp).isEquals();
    }

}
