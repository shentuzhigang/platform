package club.zstuca.platform.dto;

public class ResultDTO {
    private Integer code;
    private String message;

    public ResultDTO(Integer code,String message){
        this.code = code;
        this.message = message;
    }

}
