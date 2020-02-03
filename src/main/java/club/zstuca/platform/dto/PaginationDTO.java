package club.zstuca.platform.dto;

import java.util.ArrayList;
import java.util.List;

public class PaginationDTO {
    private List<QuestionDTO> questionDTOList;
    private boolean showPrePage;
    private boolean showFirstPage;
    private boolean showNextPage;
    private boolean showEndPage;
    private Integer nowPage;
    private Integer totalPage;
    private List<Integer> pages;

    @Override
    public String toString() {
        return "PaginationDTO{" +
                "questionDTOList=" + questionDTOList +
                ", showPrePage=" + showPrePage +
                ", showFirstPage=" + showFirstPage +
                ", showNextPage=" + showNextPage +
                ", showEndPage=" + showEndPage +
                ", nowPage=" + nowPage +
                ", totolPage=" + totalPage +
                ", pages=" + pages +
                '}';
    }


    public List<QuestionDTO> getQuestionDTOList() {
        return questionDTOList;
    }

    public void setQuestionDTOList(List<QuestionDTO> questionDTOList) {
        this.questionDTOList = questionDTOList;
    }

    public boolean isShowPrePage() {
        return showPrePage;
    }

    public void setShowPrePage(boolean showPrePage) {
        this.showPrePage = showPrePage;
    }

    public boolean isShowFirstPage() {
        return showFirstPage;
    }

    public void setShowFirstPage(boolean showFirstPage) {
        this.showFirstPage = showFirstPage;
    }

    public boolean isShowNextPage() {
        return showNextPage;
    }

    public void setShowNextPage(boolean showNextPage) {
        this.showNextPage = showNextPage;
    }

    public boolean isShowEndPage() {
        return showEndPage;
    }

    public void setShowEndPage(boolean showEndPage) {
        this.showEndPage = showEndPage;
    }

    public Integer getNowPage() {
        return nowPage;
    }

    public void setNowPage(Integer nowPage) {
        this.nowPage = nowPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }


    public void setPagination(Integer totalCount, Integer page, Integer size) {
        if(totalCount % size ==0){
            this.totalPage = totalCount / size;
        }else{
            this.totalPage = totalCount / size + 1;
        }
        if(page < 1){
            page = 1;
        }
        if(page > totalPage){
            page = totalPage;
        }
        this.nowPage = page;
        this.pages = new ArrayList<>();
        this.pages.add(page);
        for(int i = 1;i <= 3;i++){
            if(this.nowPage - i >= 1 ){
                this.pages.add(0,page - i);
            }
            if(this.nowPage + i <= this.totalPage){
                this.pages.add(this.nowPage + i);
            }
        }

        this.showFirstPage = (!this.pages.contains(1));
        this.showPrePage = (page != 1);
        this.showEndPage = (!this.pages.contains(this.totalPage));
        this.showNextPage = (!page.equals(this.totalPage));

    }
}
