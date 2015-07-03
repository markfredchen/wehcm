package com.mcworkshop.wehcm.core.domain.flow;

/**
 * Created by markfredchen on 6/15/15.
 */
public class PassiveFlow extends Flow {
    private String targetPath;

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }
}
