
package com.autobuds.PMDReportAggregator.utility;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "beginline",
    "begincolumn",
    "endline",
    "endcolumn",
    "description",
    "rule",
    "ruleset",
    "priority",
    "externalInfoUrl"
})
public class Violation implements Serializable
{

    @JsonProperty("beginline")
    private Integer beginline;
    @JsonProperty("begincolumn")
    private Integer begincolumn;
    @JsonProperty("endline")
    private Integer endline;
    @JsonProperty("endcolumn")
    private Integer endcolumn;
    @JsonProperty("description")
    private String description;
    @JsonProperty("rule")
    private String rule;
    @JsonProperty("ruleset")
    private String ruleset;
    @JsonProperty("priority")
    private Integer priority;
    @JsonProperty("externalInfoUrl")
    private String externalInfoUrl;
    private final static long serialVersionUID = 2823855782773360971L;

    @JsonProperty("beginline")
    public Integer getBeginline() {
        return beginline;
    }

    @JsonProperty("beginline")
    public void setBeginline(Integer beginline) {
        this.beginline = beginline;
    }

    @JsonProperty("begincolumn")
    public Integer getBegincolumn() {
        return begincolumn;
    }

    @JsonProperty("begincolumn")
    public void setBegincolumn(Integer begincolumn) {
        this.begincolumn = begincolumn;
    }

    @JsonProperty("endline")
    public Integer getEndline() {
        return endline;
    }

    @JsonProperty("endline")
    public void setEndline(Integer endline) {
        this.endline = endline;
    }

    @JsonProperty("endcolumn")
    public Integer getEndcolumn() {
        return endcolumn;
    }

    @JsonProperty("endcolumn")
    public void setEndcolumn(Integer endcolumn) {
        this.endcolumn = endcolumn;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("rule")
    public String getRule() {
        return rule;
    }

    @JsonProperty("rule")
    public void setRule(String rule) {
        this.rule = rule;
    }

    @JsonProperty("ruleset")
    public String getRuleset() {
        return ruleset;
    }

    @JsonProperty("ruleset")
    public void setRuleset(String ruleset) {
        this.ruleset = ruleset;
    }

    @JsonProperty("priority")
    public Integer getPriority() {
        return priority;
    }

    @JsonProperty("priority")
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @JsonProperty("externalInfoUrl")
    public String getExternalInfoUrl() {
        return externalInfoUrl;
    }

    @JsonProperty("externalInfoUrl")
    public void setExternalInfoUrl(String externalInfoUrl) {
        this.externalInfoUrl = externalInfoUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("beginline", beginline).append("begincolumn", begincolumn).append("endline", endline).append("endcolumn", endcolumn).append("description", description).append("rule", rule).append("ruleset", ruleset).append("priority", priority).append("externalInfoUrl", externalInfoUrl).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(endline).append(beginline).append(ruleset).append(begincolumn).append(description).append(rule).append(externalInfoUrl).append(priority).append(endcolumn).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Violation) == false) {
            return false;
        }
        Violation rhs = ((Violation) other);
        return new EqualsBuilder().append(endline, rhs.endline).append(beginline, rhs.beginline).append(ruleset, rhs.ruleset).append(begincolumn, rhs.begincolumn).append(description, rhs.description).append(rule, rhs.rule).append(externalInfoUrl, rhs.externalInfoUrl).append(priority, rhs.priority).append(endcolumn, rhs.endcolumn).isEquals();
    }

}
