package io.bandit.limbo.limbo.application.api.presenters.vnderror;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VndError implements Serializable {

    @JsonProperty(value = "total")
    @ApiModelProperty(name = "total", required = false, position = 0)
    private Integer total = 1;

    @JsonProperty(value = "_embedded")
    @ApiModelProperty(name = "_embedded", required = true, position = 1)
    private VndErrors embedded;

    public VndError() {

    }

    public VndError(final Integer total, final VndErrors embedded) {
        this.total = total;
        this.embedded = embedded;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(final Integer total) {
        this.total = total;
    }

    public VndErrors getEmbedded() {
        return embedded;
    }

    public void setEmbedded(final VndErrors embedded) {
        this.embedded = embedded;
    }

    public static class VndErrors implements Serializable {

        @JsonProperty(value = "errors")
        @ApiModelProperty(name = "errors", required = false, position = 0)
        private ArrayList<Error> errors = new ArrayList<>();

        public VndErrors(final ArrayList<Error> errors) {
            this.errors = errors;
        }

        public ArrayList<Error> getErrors() {
            return errors;
        }

        public void addError(final Error error) {
            errors.add(error);
        }
    }

    public static class Error implements Serializable {

        @JsonProperty(value = "message")
        @ApiModelProperty(name = "message", required = false, position = 1)
        private String message;

        @JsonProperty(value = "path")
        @ApiModelProperty(name = "path", required = false, position = 1)
        private String path;

        public Error() {

        }

        public Error(final String message) {
            this.message = message;
            this.path = "/";
        }

        public Error(final String message, final String path) {
            this.message = message;
            this.path = path;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
