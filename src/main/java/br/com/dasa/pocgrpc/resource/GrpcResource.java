package br.com.dasa.pocgrpc.resource;

import br.com.dasa.pocgrpc.AvailabilityRequest;
import br.com.dasa.pocgrpc.AvailabilityResponse;
import br.com.dasa.pocgrpc.ReceiverTestServiceGrpc;
import br.com.dasa.pocgrpc.ScheduleResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.stream.Collectors;

@GrpcService
public class GrpcResource extends ReceiverTestServiceGrpc.ReceiverTestServiceImplBase {

    @Override
    public void receive(AvailabilityRequest request, StreamObserver<AvailabilityResponse> responseObserver) {
        var schedules = request.getScheduleList().stream()
                .map(schedule -> ScheduleResponse.newBuilder()
                        .setBrandId(schedule.getBrandId())
                        .setLocationId(schedule.getLocationId())
                        .setRoomId(schedule.getRoomId())
                        .setStartTime(schedule.getStartHour())
                        .setEndTime(schedule.getEndHour())
                        .addAllExam(schedule.getExamList())
                .build())
                .collect(Collectors.toList());

        responseObserver.onNext(AvailabilityResponse.newBuilder().addAllResponse(schedules).build());
        responseObserver.onCompleted();
    }
}
