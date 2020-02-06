package club.zstuca.platform.dto;

public class CommentDTO {
    private Long parentId;
    private String content;
    private Integer type;

    @Override
    public String toString() {
        return "CommentDTO{" +
                "parentId=" + parentId +
                ", content='" + content + '\'' +
                ", type=" + type +
                '}';
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
