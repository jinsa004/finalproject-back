package shop.mtcoding.finalproject.domain.reportReview;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import shop.mtcoding.finalproject.dto.reportReview.ReportCeoInfoRespDto;
import shop.mtcoding.finalproject.dto.reportReview.ReportCustomerInfoRespDto;
import shop.mtcoding.finalproject.dto.reportReview.ReportCeoReviewRespDto;

@Repository
public class ReportReviewRepositoryQuery {

        private final Logger log = LoggerFactory.getLogger(getClass());

        @Autowired
        private EntityManager em;

        public ReportCeoInfoRespDto findByReportReviewIdToCeo(Long reportReviewId) {

                StringBuffer sb = new StringBuffer();
                sb.append(
                                "select cr.name storeName, cr.content comment from report_reviews rr inner join ");
                sb.append(
                                "(select s.name, cr.content, cr.id from ceo_reviews cr inner join stores s on cr.store_id = s.id) ");
                sb.append("cr on rr.ceo_review_id = cr.id where rr.id = :reportReviewId");

                // 쿼리 완성
                Query query = em.createNativeQuery(sb.toString()).setParameter("reportReviewId", reportReviewId);

                log.debug("디버그 : 통과? ");
                log.debug("디버그 : 쿼리완성 - ");

                // 쿼리 실행 (qlrm 라이브러리 필요 = DTO에 DB결과를 매핑하기 위해서)
                JpaResultMapper result = new JpaResultMapper();

                log.debug("디버그 : 통과?? ");

                ReportCeoInfoRespDto reportCeoReviewRespDto = result.uniqueResult(query,
                                ReportCeoInfoRespDto.class);

                return reportCeoReviewRespDto;
        }

        public ReportCustomerInfoRespDto findByReportReviewId(Long reportReviewId) {

                StringBuffer sb = new StringBuffer();
                sb.append(
                                "select rr.id id, rr.reason reason, crr.content content, crr.nickname nickname from report_reviews rr inner join ");
                sb.append(
                                "(select cr.content, cr.id, u.nickname from customer_reviews cr inner join users u on cr.user_id = u.id) crr ");
                sb.append("on rr.customer_review_id = crr.id where rr.id = :reportReviewId");

                // 쿼리 완성
                Query query = em.createNativeQuery(sb.toString()).setParameter("reportReviewId", reportReviewId);

                log.debug("디버그 : 통과? ");
                log.debug("디버그 : 쿼리완성 - ");

                // 쿼리 실행 (qlrm 라이브러리 필요 = DTO에 DB결과를 매핑하기 위해서)
                JpaResultMapper result = new JpaResultMapper();

                log.debug("디버그 : 통과?? ");

                ReportCustomerInfoRespDto reportCustomerReviewRespDto = result.uniqueResult(query,
                                ReportCustomerInfoRespDto.class);

                return reportCustomerReviewRespDto;
        }

        public List<ReportCeoReviewRespDto> findAllByStoreId(Long storeId) {

                StringBuffer sb = new StringBuffer();
                sb.append(
                                "select repr.id report_review_id, cusr.star_point, cusr.photo, o.id order_id, o.created_at, cusr.is_closure, repr.is_resolve, repr.admin_comment from report_reviews repr ");
                sb.append("left outer join customer_reviews cusr on cusr.id = repr.customer_review_id ");
                sb.append("left outer join orders o on o.id = cusr.order_id ");
                sb.append("left outer join stores s on s.id = cusr.store_id ");
                sb.append("where s.id = :storeId");

                // 쿼리 완성
                // Query query = em.createNativeQuery(sb, storeId);
                Query query = em.createNativeQuery(sb.toString()).setParameter("storeId", storeId);

                log.debug("디버그 : 통과? ");
                log.debug("디버그 : 쿼리완성 - ");

                // 쿼리 실행 (qlrm 라이브러리 필요 = DTO에 DB결과를 매핑하기 위해서)
                JpaResultMapper result = new JpaResultMapper();

                log.debug("디버그 : 통과?? ");

                List<ReportCeoReviewRespDto> reportReviewRespDtos = result.list(query,
                                ReportCeoReviewRespDto.class);

                log.debug("디버그 : 통과?? ");
                log.debug("디버그 : " + reportReviewRespDtos.get(0).getReportReviewId());
                log.debug("디버그 : " + reportReviewRespDtos.get(0).getStarPoint());
                log.debug("디버그 : " + reportReviewRespDtos.get(0).getCreatedAt());
                return reportReviewRespDtos;
        }
}
