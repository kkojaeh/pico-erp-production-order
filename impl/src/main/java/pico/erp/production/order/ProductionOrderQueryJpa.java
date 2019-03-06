package pico.erp.production.order;

import static org.springframework.util.StringUtils.isEmpty;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.production.order.ProductionOrderView.Filter;
import pico.erp.shared.Public;
import pico.erp.shared.jpa.QueryDslJpaSupport;

@Service
@Public
@Transactional(readOnly = true)
@Validated
public class ProductionOrderQueryJpa implements ProductionOrderQuery {


  private final QProductionOrderEntity request = QProductionOrderEntity.productionOrderEntity;

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private QueryDslJpaSupport queryDslJpaSupport;

  @Override
  public Page<ProductionOrderView> retrieve(Filter filter, Pageable pageable) {
    val query = new JPAQuery<ProductionOrderView>(entityManager);
    val select = Projections.bean(ProductionOrderView.class,
      request.id,
      request.code,
      request.itemId,
      request.processId,
      request.itemSpecCode,
      request.quantity,
      request.spareQuantity,
      request.progressedQuantity,
      request.erroredQuantity,
      request.unit,
      request.ordererId,
      request.accepterId,
      request.projectId,
      request.receiverId,
      request.receiveSiteId,
      request.receiveStationId,
      request.dueDate,
      request.committedDate,
      request.completedDate,
      request.acceptedDate,
      request.rejectedDate,
      request.canceledDate,
      request.status,
      request.createdBy,
      request.createdDate
    );
    query.select(select);
    query.from(request);

    val builder = new BooleanBuilder();

    if (!isEmpty(filter.getCode())) {
      builder.and(request.code.value
        .likeIgnoreCase(queryDslJpaSupport.toLikeKeyword("%", filter.getCode(), "%")));
    }

    if (filter.getReceiverId() != null) {
      builder.and(request.receiverId.eq(filter.getReceiverId()));
    }

    if (filter.getOrdererId() != null) {
      builder.and(request.ordererId.eq(filter.getOrdererId()));
    }

    if (filter.getAccepterId() != null) {
      builder.and(request.accepterId.eq(filter.getAccepterId()));
    }

    if (filter.getProjectId() != null) {
      builder.and(request.projectId.eq(filter.getProjectId()));
    }

    if (filter.getItemId() != null) {
      builder.and(request.itemId.eq(filter.getItemId()));
    }

    if (filter.getStatuses() != null && !filter.getStatuses().isEmpty()) {
      builder.and(request.status.in(filter.getStatuses()));
    }

    if (filter.getStartDueDate() != null) {
      builder.and(request.dueDate.goe(filter.getStartDueDate()));
    }
    if (filter.getEndDueDate() != null) {
      builder.and(request.dueDate.loe(filter.getEndDueDate()));
    }

    query.where(builder);
    return queryDslJpaSupport.paging(query, pageable, select);
  }

  @Override
  public Page<ProductionOrderAwaitExecutionView> retrieve(ProductionOrderAwaitExecutionView.Filter filter,
    Pageable pageable) {
    val query = new JPAQuery<ProductionOrderAwaitExecutionView>(entityManager);
    val select = Projections.bean(ProductionOrderAwaitExecutionView.class,
      request.id,
      request.code,
      request.itemId,
      request.processId,
      request.itemSpecCode,
      request.quantity,
      request.spareQuantity,
      request.unit,
      request.ordererId,
      request.projectId,
      request.receiverId,
      request.receiveSiteId,
      request.receiveStationId,
      request.committedDate,
      request.acceptedDate,
      request.dueDate,
      request.createdDate
    );
    query.select(select);
    query.from(request);

    val builder = new BooleanBuilder();

    builder.and(request.status.in(ProductionOrderStatusKind.IN_PLANNING, ProductionOrderStatusKind.IN_PROGRESS));

    if (filter.getReceiverId() != null) {
      builder.and(request.receiverId.eq(filter.getReceiverId()));
    }

    if (filter.getOrdererId() != null) {
      builder.and(request.ordererId.eq(filter.getOrdererId()));
    }

    if (filter.getProjectId() != null) {
      builder.and(request.projectId.eq(filter.getProjectId()));
    }

    if (filter.getItemId() != null) {
      builder.and(
        request.itemId.eq(filter.getItemId())
      );
    }

    if (filter.getStartDueDate() != null) {
      builder.and(request.dueDate.goe(filter.getStartDueDate()));
    }
    if (filter.getEndDueDate() != null) {
      builder.and(request.dueDate.loe(filter.getEndDueDate()));
    }

    query.where(builder);
    return queryDslJpaSupport.paging(query, pageable, select);
  }

  @Override
  public Page<ProductionOrderAwaitAcceptView> retrieve(ProductionOrderAwaitAcceptView.Filter filter,
    Pageable pageable) {
    val query = new JPAQuery<ProductionOrderAwaitAcceptView>(entityManager);
    val select = Projections.bean(ProductionOrderAwaitAcceptView.class,
      request.id,
      request.code,
      request.itemId,
      request.processId,
      request.itemSpecCode,
      request.quantity,
      request.spareQuantity,
      request.unit,
      request.ordererId,
      request.projectId,
      request.receiverId,
      request.receiveSiteId,
      request.receiveStationId,
      request.committedDate,
      request.dueDate
    );
    query.select(select);
    query.from(request);

    val builder = new BooleanBuilder();

    builder.and(request.status.eq(ProductionOrderStatusKind.COMMITTED));

    if (!isEmpty(filter.getCode())) {
      builder.and(request.code.value
        .likeIgnoreCase(queryDslJpaSupport.toLikeKeyword("%", filter.getCode(), "%")));
    }

    if (filter.getReceiverId() != null) {
      builder.and(request.receiverId.eq(filter.getReceiverId()));
    }

    if (filter.getOrdererId() != null) {
      builder.and(request.ordererId.eq(filter.getOrdererId()));
    }

    if (filter.getProjectId() != null) {
      builder.and(request.projectId.eq(filter.getProjectId()));
    }

    if (filter.getItemId() != null) {
      builder.and(request.itemId.eq(filter.getItemId()));
    }

    if (filter.getStartDueDate() != null) {
      builder.and(request.dueDate.goe(filter.getStartDueDate()));
    }
    if (filter.getEndDueDate() != null) {
      builder.and(request.dueDate.loe(filter.getEndDueDate()));
    }

    query.where(builder);
    return queryDslJpaSupport.paging(query, pageable, select);
  }
}
