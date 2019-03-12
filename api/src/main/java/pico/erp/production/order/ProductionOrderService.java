package pico.erp.production.order;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface ProductionOrderService {

  void accept(@Valid @NotNull ProductionOrderRequests.AcceptRequest request);

  void cancel(@Valid @NotNull ProductionOrderRequests.CancelRequest request);

  void commit(@Valid @NotNull ProductionOrderRequests.CommitRequest request);

  void complete(@Valid @NotNull ProductionOrderRequests.CompleteRequest request);

  ProductionOrderData create(@Valid @NotNull ProductionOrderRequests.CreateRequest request);

  boolean exists(@Valid @NotNull ProductionOrderId id);

  ProductionOrderData get(@Valid @NotNull ProductionOrderId id);

  void plan(@Valid @NotNull ProductionOrderRequests.PlanRequest request);

  void progress(@Valid @NotNull ProductionOrderRequests.ProgressRequest request);

  void prepare(@Valid @NotNull ProductionOrderRequests.PrepareRequest request);

  void update(@Valid @NotNull ProductionOrderRequests.UpdateRequest request);

  void reject(@Valid @NotNull ProductionOrderRequests.RejectRequest request);

}
