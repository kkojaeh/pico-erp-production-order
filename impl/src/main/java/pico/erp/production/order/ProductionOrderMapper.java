package pico.erp.production.order;

import java.util.Optional;
import kkojaeh.spring.boot.component.ComponentAutowired;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.AuditorAware;
import pico.erp.company.CompanyData;
import pico.erp.company.CompanyId;
import pico.erp.company.CompanyService;
import pico.erp.item.ItemData;
import pico.erp.item.ItemId;
import pico.erp.item.ItemService;
import pico.erp.item.spec.ItemSpecData;
import pico.erp.item.spec.ItemSpecId;
import pico.erp.item.spec.ItemSpecService;
import pico.erp.project.ProjectData;
import pico.erp.project.ProjectId;
import pico.erp.project.ProjectService;
import pico.erp.shared.data.Auditor;
import pico.erp.user.UserData;
import pico.erp.user.UserId;
import pico.erp.user.UserService;
import pico.erp.warehouse.location.site.SiteData;
import pico.erp.warehouse.location.site.SiteId;
import pico.erp.warehouse.location.site.SiteService;
import pico.erp.warehouse.location.station.StationData;
import pico.erp.warehouse.location.station.StationId;
import pico.erp.warehouse.location.station.StationService;

@Mapper
public abstract class ProductionOrderMapper {

  @Autowired
  protected AuditorAware<Auditor> auditorAware;

  @ComponentAutowired
  protected ItemService itemService;

  @ComponentAutowired
  protected ItemSpecService itemSpecService;

  @Autowired
  protected ProductionOrderCodeGenerator productionOrderCodeGenerator;

  @ComponentAutowired
  private CompanyService companyService;

  @ComponentAutowired
  private UserService userService;

  @Lazy
  @Autowired
  private ProductionOrderRepository productionOrderRepository;

  @ComponentAutowired
  private ProjectService projectService;

  @ComponentAutowired
  private SiteService siteService;

  @ComponentAutowired
  private StationService stationService;

  protected Auditor auditor(UserId userId) {
    return Optional.ofNullable(userId)
      .map(userService::getAuditor)
      .orElse(null);
  }

  @Mappings({
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract ProductionOrderEntity jpa(ProductionOrder data);

  public ProductionOrder jpa(ProductionOrderEntity entity) {
    return ProductionOrder.builder()
      .id(entity.getId())
      .code(entity.getCode())
      .itemId(entity.getItemId())
      .processId(entity.getProcessId())
      .itemSpecCode(entity.getItemSpecCode())
      .quantity(entity.getQuantity())
      .spareQuantity(entity.getSpareQuantity())
      .progressedQuantity(entity.getProgressedQuantity())
      .erroredQuantity(entity.getErroredQuantity())
      .unit(entity.getUnit())
      .projectId(entity.getProjectId())
      .dueDate(entity.getDueDate())
      .receiverId(entity.getReceiverId())
      .receiveSiteId(entity.getReceiveSiteId())
      .receiveStationId(entity.getReceiveStationId())
      .remark(entity.getRemark())
      .ordererId(entity.getOrdererId())
      .accepterId(entity.getAccepterId())
      .committedDate(entity.getCommittedDate())
      .acceptedDate(entity.getAcceptedDate())
      .completedDate(entity.getCompletedDate())
      .rejectedDate(entity.getRejectedDate())
      .canceledDate(entity.getCanceledDate())
      .status(entity.getStatus())
      .rejectedReason(entity.getRejectedReason())
      .estimatedPreparedDate(entity.getEstimatedPreparedDate())
      .preparedDate(entity.getPreparedDate())
      .build();
  }

  protected UserData map(UserId userId) {
    return Optional.ofNullable(userId)
      .map(userService::get)
      .orElse(null);
  }

  protected CompanyData map(CompanyId companyId) {
    return Optional.ofNullable(companyId)
      .map(companyService::get)
      .orElse(null);
  }

  protected ProjectData map(ProjectId projectId) {
    return Optional.ofNullable(projectId)
      .map(projectService::get)
      .orElse(null);
  }

  public ProductionOrder map(ProductionOrderId productionOrderId) {
    return Optional.ofNullable(productionOrderId)
      .map(id -> productionOrderRepository.findBy(id)
        .orElseThrow(ProductionOrderExceptions.NotFoundException::new)
      )
      .orElse(null);
  }

  protected ItemData map(ItemId itemId) {
    return Optional.ofNullable(itemId)
      .map(itemService::get)
      .orElse(null);
  }

  protected ItemSpecData map(ItemSpecId itemSpecId) {
    return Optional.ofNullable(itemSpecId)
      .map(itemSpecService::get)
      .orElse(null);
  }

  protected StationData map(StationId stationId) {
    return Optional.ofNullable(stationId)
      .map(stationService::get)
      .orElse(null);
  }

  protected SiteData map(SiteId siteId) {
    return Optional.ofNullable(siteId)
      .map(siteService::get)
      .orElse(null);
  }

  @Mappings({
  })
  public abstract ProductionOrderData map(ProductionOrder productionOrder);

  @Mappings({
    @Mapping(target = "codeGenerator", expression = "java(productionOrderCodeGenerator)")
  })
  public abstract ProductionOrderMessages.Create.Request map(
    ProductionOrderRequests.CreateRequest request);

  public abstract ProductionOrderMessages.Update.Request map(
    ProductionOrderRequests.UpdateRequest request);


  public abstract ProductionOrderMessages.Accept.Request map(
    ProductionOrderRequests.AcceptRequest request);

  public abstract ProductionOrderMessages.Commit.Request map(
    ProductionOrderRequests.CommitRequest request);

  public abstract ProductionOrderMessages.Complete.Request map(
    ProductionOrderRequests.CompleteRequest request);

  public abstract ProductionOrderMessages.Cancel.Request map(
    ProductionOrderRequests.CancelRequest request);

  public abstract ProductionOrderMessages.Reject.Request map(
    ProductionOrderRequests.RejectRequest request);

  public abstract ProductionOrderMessages.Progress.Request map(
    ProductionOrderRequests.ProgressRequest request);

  public abstract ProductionOrderMessages.Plan.Request map(
    ProductionOrderRequests.PlanRequest request);

  public abstract ProductionOrderMessages.Prepare.Request map(
    ProductionOrderRequests.PrepareRequest request);

  public abstract void pass(ProductionOrderEntity from, @MappingTarget ProductionOrderEntity to);


}


