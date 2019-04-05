package pico.erp.production.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import pico.erp.company.CompanyId;
import pico.erp.item.ItemId;
import pico.erp.item.spec.ItemSpecCode;
import pico.erp.process.ProcessId;
import pico.erp.project.ProjectId;
import pico.erp.shared.data.UnitKind;
import pico.erp.user.UserId;
import pico.erp.warehouse.location.site.SiteId;
import pico.erp.warehouse.location.station.StationId;

/**
 * 주문 접수
 */
@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductionOrder implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  ProductionOrderId id;

  ProductionOrderCode code;

  ItemId itemId;

  ProcessId processId;

  ItemSpecCode itemSpecCode;

  BigDecimal quantity;

  BigDecimal spareQuantity;

  BigDecimal progressedQuantity;

  BigDecimal erroredQuantity;

  UnitKind unit;

  ProjectId projectId;

  OffsetDateTime dueDate;

  CompanyId receiverId;

  SiteId receiveSiteId;

  StationId receiveStationId;

  String remark;

  UserId ordererId;

  UserId accepterId;

  OffsetDateTime committedDate;

  OffsetDateTime acceptedDate;

  OffsetDateTime completedDate;

  OffsetDateTime rejectedDate;

  OffsetDateTime canceledDate;

  ProductionOrderStatusKind status;

  String rejectedReason;

  OffsetDateTime estimatedPreparedDate;

  OffsetDateTime preparedDate;


  public ProductionOrder() {

  }

  public ProductionOrderMessages.Create.Response apply(
    ProductionOrderMessages.Create.Request request) {
    this.id = request.getId();
    this.itemId = request.getItemId();
    this.processId = request.getProcessId();
    this.itemSpecCode = request.getItemSpecCode();
    this.quantity = request.getQuantity();
    this.unit = request.getUnit();
    this.projectId = request.getProjectId();
    this.dueDate = request.getDueDate();
    this.spareQuantity = request.getSpareQuantity();
    this.receiverId = request.getReceiverId();
    this.receiveSiteId = request.getReceiveSiteId();
    this.receiveStationId = request.getReceiveStationId();
    this.remark = request.getRemark();
    this.status = ProductionOrderStatusKind.DRAFT;
    this.ordererId = request.getOrdererId();
    this.progressedQuantity = BigDecimal.ZERO;
    this.erroredQuantity = BigDecimal.ZERO;
    this.estimatedPreparedDate = request.getEstimatedPreparedDate();
    this.code = request.getCodeGenerator().generate(this);
    return new ProductionOrderMessages.Create.Response(
      Arrays.asList(new ProductionOrderEvents.CreatedEvent(this.id))
    );
  }

  public ProductionOrderMessages.Update.Response apply(
    ProductionOrderMessages.Update.Request request) {
    if (!isUpdatable()) {
      throw new ProductionOrderExceptions.CannotUpdateException();
    }
    this.itemId = request.getItemId();
    this.processId = request.getProcessId();
    this.itemSpecCode = request.getItemSpecCode();
    this.quantity = request.getQuantity();
    this.unit = request.getUnit();
    this.projectId = request.getProjectId();
    this.dueDate = request.getDueDate();
    this.spareQuantity = request.getSpareQuantity();
    this.receiverId = request.getReceiverId();
    this.receiveSiteId = request.getReceiveSiteId();
    this.receiveStationId = request.getReceiveStationId();
    this.estimatedPreparedDate = request.getEstimatedPreparedDate();
    this.remark = request.getRemark();
    return new ProductionOrderMessages.Update.Response(
      Arrays.asList(new ProductionOrderEvents.UpdatedEvent(this.id))
    );
  }

  public ProductionOrderMessages.Accept.Response apply(
    ProductionOrderMessages.Accept.Request request) {
    if (!isAcceptable()) {
      throw new ProductionOrderExceptions.CannotAcceptException();
    }
    this.status = ProductionOrderStatusKind.ACCEPTED;
    this.accepterId = request.getAccepterId();
    this.acceptedDate = OffsetDateTime.now();
    return new ProductionOrderMessages.Accept.Response(
      Arrays.asList(new ProductionOrderEvents.AcceptedEvent(this.id))
    );
  }

  public ProductionOrderMessages.Cancel.Response apply(
    ProductionOrderMessages.Cancel.Request request) {
    if (!isCancelable()) {
      throw new ProductionOrderExceptions.CannotCancelException();
    }
    this.status = ProductionOrderStatusKind.CANCELED;
    this.canceledDate = OffsetDateTime.now();
    return new ProductionOrderMessages.Cancel.Response(
      Arrays.asList(new ProductionOrderEvents.CanceledEvent(this.id))
    );
  }

  public ProductionOrderMessages.Complete.Response apply(
    ProductionOrderMessages.Complete.Request request) {
    if (!isCompletable()) {
      throw new ProductionOrderExceptions.CannotCompleteException();
    }
    this.status = ProductionOrderStatusKind.COMPLETED;
    this.completedDate = OffsetDateTime.now();
    return new ProductionOrderMessages.Complete.Response(
      Arrays.asList(new ProductionOrderEvents.CompletedEvent(this.id))
    );
  }

  public ProductionOrderMessages.Commit.Response apply(
    ProductionOrderMessages.Commit.Request request) {
    if (!isCommittable() || !ordererId.equals(request.getCommitterId())) {
      throw new ProductionOrderExceptions.CannotCommitException();
    }
    this.status = ProductionOrderStatusKind.COMMITTED;
    this.committedDate = OffsetDateTime.now();
    return new ProductionOrderMessages.Commit.Response(
      Arrays.asList(new ProductionOrderEvents.CommittedEvent(this.id))
    );
  }

  public ProductionOrderMessages.Progress.Response apply(
    ProductionOrderMessages.Progress.Request request) {
    if (!isProgressable()) {
      throw new ProductionOrderExceptions.CannotProgressException();
    }
    this.progressedQuantity = this.progressedQuantity.add(request.getProgressedQuantity());
    this.erroredQuantity = this.erroredQuantity.add(request.getErroredQuantity());
    this.status = ProductionOrderStatusKind.IN_PROGRESS;
    return new ProductionOrderMessages.Progress.Response(
      Arrays.asList(
        new ProductionOrderEvents.ProgressedEvent(this.id,
          request.getProgressedQuantity(),
          request.getErroredQuantity()
        )
      )
    );
  }

  public ProductionOrderMessages.Reject.Response apply(
    ProductionOrderMessages.Reject.Request request) {
    if (!isRejectable()) {
      throw new ProductionOrderExceptions.CannotRejectException();
    }
    this.status = ProductionOrderStatusKind.REJECTED;
    this.rejectedDate = OffsetDateTime.now();
    this.rejectedReason = request.getRejectedReason();
    return new ProductionOrderMessages.Reject.Response(
      Arrays.asList(new ProductionOrderEvents.RejectedEvent(this.id))
    );
  }

  public ProductionOrderMessages.Plan.Response apply(
    ProductionOrderMessages.Plan.Request request) {
    if (!this.isPlannable()) {
      throw new ProductionOrderExceptions.CannotPlanException();
    }
    this.status = ProductionOrderStatusKind.PLANNED;
    return new ProductionOrderMessages.Plan.Response(
      Arrays.asList(new ProductionOrderEvents.PlannedEvent(this.id))
    );
  }

  public ProductionOrderMessages.Prepare.Response apply(
    ProductionOrderMessages.Prepare.Request request) {
    if (!this.isPreparable()) {
      throw new ProductionOrderExceptions.CannotPrepareException();
    }
    this.status = ProductionOrderStatusKind.PREPARED;
    this.preparedDate = OffsetDateTime.now();
    return new ProductionOrderMessages.Prepare.Response(
      Arrays.asList(new ProductionOrderEvents.PreparedEvent(this.id))
    );
  }

  public boolean isAcceptable() {
    return status.isAcceptable();
  }

  public boolean isCancelable() {
    return status.isCancelable();
  }

  public boolean isCommittable() {
    return status.isCommittable();
  }

  public boolean isCompletable() {
    return status.isCompletable();
  }

  public boolean isPlannable() {
    return status.isPlannable();
  }

  public boolean isProgressable() {
    return status.isProgressable();
  }

  public boolean isRejectable() {
    return status.isRejectable();
  }

  public boolean isUpdatable() {
    return status.isUpdatable();
  }

  public boolean isPreparable() {
    return status.isPreparable();
  }


}
