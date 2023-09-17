package com.iteesoft.drone;

import com.iteesoft.drone.enums.ModelType;
import com.iteesoft.drone.enums.State;
import com.iteesoft.drone.model.Drone;
import com.iteesoft.drone.model.Medication;
import com.iteesoft.drone.repository.DroneRepository;
import com.iteesoft.drone.repository.MedicationRepository;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DroneApplication {

	public static void main(String[] args) {
		SpringApplication.run(DroneApplication.class, args);
	}

//	@Bean
//	CommandLineRunner run(MedicationRepository medicRepo, DroneRepository droneRepo) {
//	return args -> {
//		medicRepo.save(Medication.builder().name("Paracetamol").weight(12).imageUrl("http://medic7.png").code("2Fcdgs1").build());
//		medicRepo.save(Medication.builder().name("Ibuprofin").weight(10).imageUrl("http://medic3.png").code("3Dsjdw1").build());
//		medicRepo.save(Medication.builder().name("Analgesic").weight(15).imageUrl("http://medic2.png").code("5Gcesf1").build());
//		droneRepo.save(Drone.builder().serialNumber("A07ughfd54").weightLimit(50).batteryCapacity(50).model(ModelType.LIGHT_WEIGHT).state(State.IDLE).build());
//		droneRepo.save(Drone.builder().serialNumber("D46hftfd22").weightLimit(500).batteryCapacity(27).model(ModelType.HEAVY_WEIGHT).state(State.DELIVERING).build());
//		droneRepo.save(Drone.builder().serialNumber("S18uydfe43").weightLimit(100).batteryCapacity(70).model(ModelType.MIDDLE_WEIGHT).state(State.LOADED).build());
//		};
//	}

	@Bean
	public OpenAPI customOpenAPI(@Value("${application-description}") String appDescription, @Value("${application-version}") String appVersion) {
		return new OpenAPI().info(new Info().title("drone management app API")
				.version(appVersion).description(appDescription)
				.termsOfService("https://iteesoft.io/terms")
				.license(new License()
				.name("Ifeoluwa Stephen Adebayo 'iTeesoft'")
				.url("https://iteesoft.io")));
	}
}
