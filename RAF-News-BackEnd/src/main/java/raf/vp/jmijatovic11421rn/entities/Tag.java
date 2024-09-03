package raf.vp.jmijatovic11421rn.entities;

import javax.validation.constraints.NotBlank;

public class Tag {
    @NotBlank
    private String tagName;

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }
}
