
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
    "filename",
    "violations"
})
public class ApexFile implements Serializable
{

    @JsonProperty("filename")
    private String filename;
    @JsonProperty("violations")
    private List<Violation> violations = null;
    private final static long serialVersionUID = -5942935604902240042L;

    @JsonProperty("filename")
    public String getFilename() {
        return filename;
    }

    @JsonProperty("filename")
    public void setFilename(String filename) {
        this.filename = filename;
    }

    @JsonProperty("violations")
    public List<Violation> getViolations() {
        return violations;
    }

    @JsonProperty("violations")
    public void setViolations(List<Violation> violations) {
        this.violations = violations;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("filename", filename).append("violations", violations).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(filename).append(violations).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ApexFile) == false) {
            return false;
        }
        ApexFile rhs = ((ApexFile) other);
        return new EqualsBuilder().append(filename, rhs.filename).append(violations, rhs.violations).isEquals();
    }

}
