package pico.erp.production.order;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.company.CompanyId;
import pico.erp.item.ItemId;
import pico.erp.item.spec.ItemSpecCode;
import pico.erp.process.ProcessId;
import pico.erp.project.ProjectId;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.data.UnitKind;
import pico.erp.user.UserId;
import pico.erp.warehouse.location.site.SiteId;
import pico.erp.warehouse.location.station.StationId;

public interface ProductionOrderRequests {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CreateRequest {

    @Valid
    @NotNull
    ProductionOrderId id;

    @Valid
    @NotNull
    ItemId itemId;

    @Valid
    @NotNull
    ProcessId processId;

    @Valid
    @NotNull
    ItemSpecCode itemSpecCode;

    @NotNull
    @Min(0)
    BigDecimal quantity;

    @NotNull
    @Min(0)
    BigDecimal spareQuantity;

    @NotNull
    UnitKind unit;

    @Valid
    @NotNull
    ProjectId projectId;

    @Future
    @NotNull
    OffsetDateTime dueDate;

    @Valid
    @NotNull
    CompanyId receiverId;

    @Valid
    SiteId receiveSiteId;

    @Valid
    StationId receiveStationId;

    @Size(max = TypeDefinitions.REMARK_LENGTH)
    String remark;

    @Valid
    @NotNull
    UserId ordererId;

    public static CreateRequest from(ProductionOrderData data) {
      return CreateRequest.builder()
        .id(data.getId())
        .itemId(data.getItemId())
        .itemSpecCode(data.getItemSpecCode())
        .processId(data.getProcessId())
        .quantity(data.getQuantity())
        .spareQuantity(data.getSpareQuantity())
        .unit(data.getUnit())
        .projectId(data.getProjectId())
        .dueDate(data.getDueDate())
        .receiverId(data.getReceiverId())
        .receiveSiteId(data.getReceiveSiteId())
        .receiveStationId(data.getReceiveStationId())
        .remark(data.getRemark())
        .ordererId(data.getOrdererId())
        .build();
    }


  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class UpdateRequest {

    @Valid
    @NotNull
    ProductionOrderId id;

    @Valid
    @NotNull
    ItemId itemId;

    @Valid
    @NotNull
    ProcessId processId;

    @Valid
    @NotNull
    ItemSpecCode itemSpecCode;

    @NotNull
    @Min(0)
    BigDecimal quantity;

    @NotNull
    @Min(0)
    BigDecimal spareQuantity;

    @NotNull
    UnitKind unit;

    @Valid
    @NotNull
    ProjectId projectId;

    @Future
    @NotNull
    OffsetDateTime dueDate;

    @Valid
    CompanyId supplierId;

    @Valid
    @NotNull
    CompanyId receiverId;

    @Valid
    SiteId receiveSiteId;

    @Valid
    StationId receiveStationId;

    @Size(max = TypeDefinitions.REMARK_LENGTH)
    String remark;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class AcceptRequest {

    @Valid
    @NotNull
    ProductionOrderId id;

    @Valid
    @NotNull
    UserId accepterId;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CommitRequest {

    @Valid
    @NotNull
    ProductionOrderId id;

    @Valid
    @NotNull
    UserId committerId;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CompleteRequest {

    @Valid
    @NotNull
    ProductionOrderId id;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class RejectRequest {

    @Valid
    @NotNull
    ProductionOrderId id;

    @Size(max = TypeDefinitions.REMARK_LENGTH)
    String rejectedReason;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CancelRequest {

    @Valid
    @NotNull
    ProductionOrderId id;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class ProgressRequest {

    @Valid
    @NotNull
    ProductionOrderId id;

    @NotNull
    @Min(0)
    BigDecimal progressedQuantity;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class PlanRequest {

    @Valid
    @NotNull
    ProductionOrderId id;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CancelProgressRequest {

    @Valid
    @NotNull
    ProductionOrderId id;

  }

}
