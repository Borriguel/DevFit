package dev.borriguel.devfit.config;

import dev.borriguel.devfit.model.*;
import dev.borriguel.devfit.service.GymEquipmentService;
import dev.borriguel.devfit.service.GymUnitService;
import dev.borriguel.devfit.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@RequiredArgsConstructor
public class DbSeeder implements CommandLineRunner {
    private final RegistrationService registrationService;
    private final GymUnitService gymUnitService;
    private final GymEquipmentService gymEquipmentService;

    @Override
    public void run(String... args) throws Exception {
        var unit = new GymUnit("devfit", "rua dos marombas numero 572 rj", BigDecimal.valueOf(87.50));
        gymUnitService.createGymUnit(unit);

        registrationService.registerAdmin("admin@devfit.com", "devfit@admin");

        gymEquipmentService.createGymEquipment(new GymEquipment("Supino Reto", "Banco para supino reto com barra", "https://example.com/images/bench.jpg", Category.CHEST));
        gymEquipmentService.createGymEquipment(new GymEquipment("Supino Inclinado", "Banco para supino inclinado com halteres", "https://example.com/images/incline_bench.jpg", Category.CHEST));
        gymEquipmentService.createGymEquipment(new GymEquipment("Peck Deck", "Máquina para peitoral cruzado", "https://example.com/images/peck_deck.jpg", Category.CHEST));

        gymEquipmentService.createGymEquipment(new GymEquipment("Desenvolvimento com Halteres", "Halteres para desenvolvimento de ombros", "https://example.com/images/dumbbells.jpg", Category.SHOULDERS));
        gymEquipmentService.createGymEquipment(new GymEquipment("Máquina de Ombros", "Máquina específica para deltoides", "https://example.com/images/shoulder_press_machine.jpg", Category.SHOULDERS));
        gymEquipmentService.createGymEquipment(new GymEquipment("Elevação Lateral", "Banco para elevação lateral com halteres", "https://example.com/images/lateral_raise_bench.jpg", Category.SHOULDERS));

        gymEquipmentService.createGymEquipment(new GymEquipment("Barra para Supinado", "Barra reta para exercícios de bíceps", "https://example.com/images/curl_bar.jpg", Category.BICEPS));
        gymEquipmentService.createGymEquipment(new GymEquipment("Scott com Halteres", "Banco Scott com halteres", "https://example.com/images/scott_dumbbell.jpg", Category.BICEPS));
        gymEquipmentService.createGymEquipment(new GymEquipment("Rope para Bíceps", "Corda para rosca direta com polia", "https://example.com/images/rope_curl.jpg", Category.BICEPS));

        gymEquipmentService.createGymEquipment(new GymEquipment("Paralelas", "Barras paralelas para tríceps", "https://example.com/images/parallel_bars.jpg", Category.TRICEPS));
        gymEquipmentService.createGymEquipment(new GymEquipment("Máquina de Tríceps", "Máquina específica para tríceps", "https://example.com/images/tricep_machine.jpg", Category.TRICEPS));
        gymEquipmentService.createGymEquipment(new GymEquipment("Polia Alta", "Polia alta para tríceps", "https://example.com/images/high_cable.jpg", Category.TRICEPS));

        gymEquipmentService.createGymEquipment(new GymEquipment("Puxador Frontal", "Máquina para puxada frontal", "https://example.com/images/lat_pulldown.jpg", Category.BACK));
        gymEquipmentService.createGymEquipment(new GymEquipment("Remada Baixa", "Máquina para remada baixa", "https://example.com/images/seated_row.jpg", Category.BACK));
        gymEquipmentService.createGymEquipment(new GymEquipment("T-bar Row", "Máquina T-bar para remada", "https://example.com/images/tbar_row.jpg", Category.BACK));

        gymEquipmentService.createGymEquipment(new GymEquipment("Banco Infinito", "Banco ajustável para abdominais", "https://example.com/images/incline_bench_abs.jpg", Category.ABS));
        gymEquipmentService.createGymEquipment(new GymEquipment("Prancha", "Equipamento para prancha abdominal", "https://example.com/images/plank_equipment.jpg", Category.ABS));
        gymEquipmentService.createGymEquipment(new GymEquipment("Ab Wheel", "Roda abdominal para fortalecimento", "https://example.com/images/ab_wheel.jpg", Category.ABS));

        gymEquipmentService.createGymEquipment(new GymEquipment("Agachamento Smith", "Máquina Smith para agachamentos", "https://example.com/images/smith_squat.jpg", Category.LEGS));
        gymEquipmentService.createGymEquipment(new GymEquipment("Leg Press", "Máquina para pressão de pernas", "https://example.com/images/leg_press.jpg", Category.LEGS));
        gymEquipmentService.createGymEquipment(new GymEquipment("Cadeira Extensora", "Máquina para extensão de pernas", "https://example.com/images/leg_extension.jpg", Category.LEGS));

        gymEquipmentService.createGymEquipment(new GymEquipment("Esteira", "Esteira elétrica para corrida", "https://example.com/images/treadmill.jpg", Category.CARDIO));
        gymEquipmentService.createGymEquipment(new GymEquipment("Bicicleta Ergométrica", "Bicicleta para exercícios cardiovasculares", "https://example.com/images/ergometer_bike.jpg", Category.CARDIO));
        gymEquipmentService.createGymEquipment(new GymEquipment("Elíptico", "Máquina elíptica para cardio", "https://example.com/images/elliptical.jpg", Category.CARDIO));
    }
}
