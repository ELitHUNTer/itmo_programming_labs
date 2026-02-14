package lab3;

import lab3.entities.*;
import lab3.enviroment.*;
import lab3.enviroment.ground.Soil;
import lab3.enviroment.ground.SoilLayer;
import lab3.enviroment.ground.fossils.PrimitiveLife;
import lab3.messages.DefaultMessage;
import lab3.messages.RadioMessage;
import lab3.transport.Aircraft;
import lab3.transport.Ledges;
import lab3.transport.Transport;
import main.Solution;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class Lab3Main implements Solution {

    private Worker Leik, Peibody;
    private Base firstBase, secondBase;
    private List<Transport> transports;

    {
        Leik = new Worker("Leik", Profession.GEOLOGIST);
        Peibody = new Worker("Peibody", Profession.GEOLOGIST);
        firstBase = new Base(Coordinates.of(0, 0),
                new Weather(Temperature.of(10, Temperature.Scale.CELSIUS), Weather.WindType.NONE, 0));
        secondBase = new Base(Coordinates.of(20, 20),
                new Weather(Temperature.of(10, Temperature.Scale.CELSIUS), Weather.WindType.NONE, 0));
        transports = List.of(
                new Aircraft(500, 1000, 100, 50, firstBase),
                new Ledges(100, 200, 20, 10, firstBase)
        );
    }

    @Override
    public void solve() {
        Peibody.addListener((message) -> {
            if (message.getContent().contains("Need to Proceed")){
                firstBase.getRadio().send(
                        new RadioMessage("Don't", firstBase.getRadio().getFrequency()),
                        ((Aircraft)transports.get(0)).getRadio());
            }
        });
        Leik.addListener(message -> {
            if (message.getContent().contains("Don't")){
                ((Aircraft)transports.get(0)).getRadio()
                        .send(new RadioMessage("Don't care", ((Aircraft)transports.get(0)).getRadio().getFrequency()),
                                firstBase.getRadio());
            }
        });

        doFirstExpedition();
        discuss();
        doSecondExpedition();
    }

    private void doFirstExpedition() {
        Worker statist1 = new Worker("Statist 1", Profession.PILOT);
        Worker statist2 = new Worker("Statist 2", Profession.PILOT);
        Worker statist3 = new Worker("Statist 3", Profession.PILOT);
        Worker statist4 = new Worker("Statist 4", Profession.PILOT);
        Worker statist5 = new Worker("Statist 5", Profession.PILOT);

        transports.get(0).setPilot(statist1);
        ExplorableLocation locationWithPrimitiveLife = new ExplorableLocation(
                Coordinates.of(10, 0),
                new Weather(Temperature.of(10, Temperature.Scale.CELSIUS), Weather.WindType.NONE, 0),
                Soil.of(
                        new SoilLayer(SoilLayer.Type.CLAY, 100, Collections.emptyList()),
                        new SoilLayer(SoilLayer.Type.ROCK, 500, List.of(
                                new PrimitiveLife("Микробактерии", 10, Temperature.of(2, Temperature.Scale.CELSIUS))
                        ))
                )
        );

        Expedition expedition = new Expedition.Builder()
                .setStartTime(LocalDate.of(1984, 1, 11))
                .setStuff(Leik, statist1, statist2, statist3, statist4, statist5)
                .setLocations(locationWithPrimitiveLife)
                .setTransport(transports.get(0))
                .build();
        expedition.startExpedition();
        transports.get(0).unloadAllCargo();
    }

    private void discuss() {
        Worker statist1 = new Worker("Statist 1", Profession.ADVISER);
        firstBase.addCrewMember(Peibody, statist1);
        statist1.addListener(message -> {
            if (message.getContent().startsWith("1")) {
                statist1.send(new DefaultMessage("2 <Answer>"), Peibody);
            } else if (message.getContent().startsWith("3")) {
                statist1.send(new DefaultMessage("4 <Answer>"), Peibody);
            }
        });
        Peibody.addListener((message) -> {
            if (message.getContent().startsWith("2")) {
                Peibody.send(new DefaultMessage("3 <Question>"), statist1);
            }
        });
        Peibody.send(new DefaultMessage("1 <Question>"), statist1);
    }

    private void doSecondExpedition() {
        Worker statist1 = new Worker("Statist 1", Profession.PILOT);
        Worker statist2 = new Worker("Statist 2", Profession.PILOT);

        ExplorableLocation meltLocation = new ExplorableLocation(
                Coordinates.of(20, 0),
                new Weather(Temperature.of(10, Temperature.Scale.CELSIUS), Weather.WindType.NONE, 0),
                Soil.of(
                        new SoilLayer(SoilLayer.Type.CLAY, 100, Collections.emptyList()),
                        new SoilLayer(SoilLayer.Type.ROCK, 500, Collections.emptyList())
                )
        );
        meltLocation.setActionsBeforeExploration((exp) -> {
            ShortWaveRadio radio = exp.getTransport().stream()
                    .filter(x -> x instanceof Aircraft)
                    .findFirst()
                    .map(x -> (Aircraft) x)
                    .get()
                    .getRadio();
            radio.send(new RadioMessage("Arrived. Starting to melt", radio.getFrequency()), firstBase.getRadio());
        });
        meltLocation.setActionsAfterExploration(
                (exp) -> {
                    ShortWaveRadio radio = exp.getTransport().stream()
                            .filter(x -> x instanceof Aircraft)
                            .findFirst()
                            .map(x -> (Aircraft) x)
                            .get()
                            .getRadio();
                    radio.send(new RadioMessage("Bomb has been planted", radio.getFrequency()), firstBase.getRadio());
                },
                (exp) -> {
                    ShortWaveRadio radio = exp.getTransport().stream()
                            .filter(x -> x instanceof Aircraft)
                            .findFirst()
                            .map(x -> (Aircraft) x)
                            .get()
                            .getRadio();
                    radio.send(new RadioMessage("Need to Proceed", radio.getFrequency()), firstBase.getRadio());
                }
        );

        ExplorableLocation mountainLocation = new ExplorableLocation(
                Coordinates.of(20, 0),
                new Weather(Temperature.of(-20, Temperature.Scale.CELSIUS), Weather.WindType.NONE, 0),
                Soil.of(
                        new SoilLayer(SoilLayer.Type.ROCK, 50000, Collections.emptyList())
                )
        );
        mountainLocation.setActionsBeforeExploration();

        Expedition expedition = new Expedition.Builder()
                .setStartTime(LocalDate.of(1984, 1, 22))
                .setStuff(Leik, statist1, statist2)
                .setLocations(meltLocation)
                .setTransport(transports.get(0))
                .build();
        expedition.startExpedition();
    }
}
