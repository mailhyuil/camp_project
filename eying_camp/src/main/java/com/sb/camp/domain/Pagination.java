package com.sb.camp.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Pagination {
    private int PAGE_SIZE; // 한번에 보여줄 페이지 수
    private int LIST_SIZE; // 한번에 보여줄 리스트 수
    private int startPage; // 한번에 보이는 페이지 중 첫번째 번호
    private int endPage; //한번에 보이는 페이지 중 마지막 번호
    private int theLastPage; // 마지막 페이지 번호
    private int currentPage; // 현재 페이지
    private int cri; // criterion or cursor // 조회시 사용할 기준(커서) 역할

    private boolean prev;
    private boolean next;

    /**
     * @Author sangb
     * @Date 2022. 8. 28.
     * @Method paginate
     * @param currentPage
     * @param totalListSize
     * @param PAGE_SIZE
     * @param LIST_SIZE
     * @return void
     */
    public void paginate(int currentPage, int totalListSize, int PAGE_SIZE, int LIST_SIZE) {
        this.PAGE_SIZE = PAGE_SIZE;
        this.LIST_SIZE = LIST_SIZE;

        this.theLastPage = (int) Math.ceil(totalListSize / (double) LIST_SIZE);
        
        if(this.theLastPage == 0) {
        	this.theLastPage = 1;
        }
        
        this.currentPage = currentPage;

        this.endPage = (int) (Math.ceil(currentPage / (double) PAGE_SIZE)) * PAGE_SIZE;
        this.endPage = endPage > theLastPage ? theLastPage : endPage; // 마지막 번호가 최종 페이지 번호보다 크면 최종 페이지 번호 반환

        this.startPage = this.endPage - (PAGE_SIZE - 1);
        this.startPage = startPage < 1 ? 1 : startPage; // 시작번호가 1보다 작으면 1을 반환

        this.cri = (currentPage - 1) * LIST_SIZE;

        //이전 버튼 상태
        this.prev = startPage == 1 ? false : true;

        //다음 버튼 상태
        this.next = currentPage >= theLastPage ? false : true;
    }
}
