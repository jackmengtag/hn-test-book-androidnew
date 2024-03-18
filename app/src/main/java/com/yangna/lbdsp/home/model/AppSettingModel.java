package com.yangna.lbdsp.home.model;

import com.yangna.lbdsp.common.base.BaseModel;

public class AppSettingModel extends BaseModel {
    private AppVersion body;

    public AppVersion getBody() {
        return body;
    }

    public void setBody(AppVersion body) {
        this.body = body;
    }

    public class AppVersion {
        private String versions;
        private String constraint; //是否强制升级 0是 1否
        private String url;

        public String getVersions() {
            return versions;
        }

        public void setVersions(String versions) {
            this.versions = versions;
        }

        public String getConstraint() {
            return constraint;
        }

        public void setConstraint(String constraint) {
            this.constraint = constraint;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
