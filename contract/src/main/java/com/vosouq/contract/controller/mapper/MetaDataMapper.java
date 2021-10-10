package com.vosouq.contract.controller.mapper;

import com.vosouq.contract.controller.dto.MetaDataItem;
import com.vosouq.contract.controller.dto.MetaDataResponse;
import com.vosouq.contract.model.*;

import java.util.ArrayList;
import java.util.List;

public class MetaDataMapper {

    public static List<MetaDataResponse> makeVehicleMetaData() {

        List<MetaDataResponse> metaDataResponseList = new ArrayList<>();

        List<MetaDataItem> colorItems = new ArrayList<>();
        colorItems.add(MetaDataItem.build(Color.WHITE.name()));
        colorItems.add(MetaDataItem.build(Color.BLACK.name()));
        colorItems.add(MetaDataItem.build(Color.GRAY.name()));
        colorItems.add(MetaDataItem.build(Color.SILVER.name()));
        colorItems.add(MetaDataItem.build(Color.SHELL_WHITE.name()));
        colorItems.add(MetaDataItem.build(Color.BROWN.name()));
        colorItems.add(MetaDataItem.build(Color.GRAPHITE.name()));
        colorItems.add(MetaDataItem.build(Color.BLUE.name()));
        colorItems.add(MetaDataItem.build(Color.RED.name()));
        colorItems.add(MetaDataItem.build(Color.NAVY_BLUE.name()));
        colorItems.add(MetaDataItem.build(Color.BEIGE.name()));
        colorItems.add(MetaDataItem.build(Color.TITANIUM.name()));
        colorItems.add(MetaDataItem.build(Color.LEADEN.name()));
        colorItems.add(MetaDataItem.build(Color.CARBON_BLACK.name()));
        colorItems.add(MetaDataItem.build(Color.MAROON.name()));
        colorItems.add(MetaDataItem.build(Color.SILVER_BLUE.name()));
        colorItems.add(MetaDataItem.build(Color.YELLOW.name()));
        colorItems.add(MetaDataItem.build(Color.COAL.name()));
        colorItems.add(MetaDataItem.build(Color.COPPER.name()));
        colorItems.add(MetaDataItem.build(Color.CYAN.name()));
        colorItems.add(MetaDataItem.build(Color.JASPER.name()));
        colorItems.add(MetaDataItem.build(Color.DOLPHIN.name()));
        colorItems.add(MetaDataItem.build(Color.OLIVE.name()));
        colorItems.add(MetaDataItem.build(Color.GOLD.name()));
        colorItems.add(MetaDataItem.build(Color.ORANGE.name()));
        colorItems.add(MetaDataItem.build(Color.CHERRY.name()));
        colorItems.add(MetaDataItem.build(Color.CHROME.name()));
        colorItems.add(MetaDataItem.build(Color.ATLAS.name()));
        colorItems.add(MetaDataItem.build(Color.DARK_GRAY.name()));
        colorItems.add(MetaDataItem.build(Color.BARBERRY.name()));
        colorItems.add(MetaDataItem.build(Color.PURPLE.name()));
        colorItems.add(MetaDataItem.build(Color.JUJUBE.name()));
        colorItems.add(MetaDataItem.build(Color.BRONZE.name()));
        colorItems.add(MetaDataItem.build(Color.SOIL.name()));
        colorItems.add(MetaDataItem.build(Color.ONION.name()));
        metaDataResponseList.add(new MetaDataResponse("Color", colorItems));

        List<MetaDataItem> gearboxItems = new ArrayList<>();
        gearboxItems.add(MetaDataItem.build(Gearbox.AUTOMATIC.name()));
        gearboxItems.add(MetaDataItem.build(Gearbox.SEMI_AUTOMATIC.name()));
        gearboxItems.add(MetaDataItem.build(Gearbox.MANUAL.name()));
        metaDataResponseList.add(new MetaDataResponse("Gearbox", gearboxItems));

        List<MetaDataItem> fuelItems = new ArrayList<>();
        fuelItems.add(MetaDataItem.build(Fuel.PETROL.name()));
        fuelItems.add(MetaDataItem.build(Fuel.GAS.name()));
        fuelItems.add(MetaDataItem.build(Fuel.GAS_AND_PETROL.name()));
        fuelItems.add(MetaDataItem.build(Fuel.GASOLINE.name()));
        fuelItems.add(MetaDataItem.build(Fuel.HYBRID.name()));
        fuelItems.add(MetaDataItem.build(Fuel.ELECTRICAL.name()));
        metaDataResponseList.add(new MetaDataResponse("Fuel", fuelItems));

        List<MetaDataItem> bodyStatusItems = new ArrayList<>();
        bodyStatusItems.add(MetaDataItem.build(BodyStatus.NOT_COLORED.name()));
        bodyStatusItems.add(MetaDataItem.build(BodyStatus.SMOOTHED_NOT_COLORED.name()));
        bodyStatusItems.add(MetaDataItem.build(BodyStatus.ONE_SPOT_COLORED.name()));
        bodyStatusItems.add(MetaDataItem.build(BodyStatus.TWO_SPOT_COLORED.name()));
        bodyStatusItems.add(MetaDataItem.build(BodyStatus.MULTI_SPOT_COLORED.name()));
        bodyStatusItems.add(MetaDataItem.build(BodyStatus.MUDGUARD_COLORED.name()));
        bodyStatusItems.add(MetaDataItem.build(BodyStatus.MUDGUARD_CHANGED.name()));
        bodyStatusItems.add(MetaDataItem.build(BodyStatus.ONE_DOOR_COLORED.name()));
        bodyStatusItems.add(MetaDataItem.build(BodyStatus.TWO_DOOR_COLORED.name()));
        bodyStatusItems.add(MetaDataItem.build(BodyStatus.DOOR_CHANGED.name()));
        bodyStatusItems.add(MetaDataItem.build(BodyStatus.HOOD_COLOR.name()));
        bodyStatusItems.add(MetaDataItem.build(BodyStatus.HOOD_CHANGED.name()));
        bodyStatusItems.add(MetaDataItem.build(BodyStatus.ROUND_COLORED.name()));
        bodyStatusItems.add(MetaDataItem.build(BodyStatus.COMPLETE_COLORED.name()));
        bodyStatusItems.add(MetaDataItem.build(BodyStatus.CRASHED.name()));
        bodyStatusItems.add(MetaDataItem.build(BodyStatus.ROOM_CHANGED.name()));
        bodyStatusItems.add(MetaDataItem.build(BodyStatus.BURNED.name()));
        bodyStatusItems.add(MetaDataItem.build(BodyStatus.SCRAPED.name()));
        metaDataResponseList.add(new MetaDataResponse("BodyStatus", bodyStatusItems));

        return metaDataResponseList;

    }


    public static List<MetaDataResponse> makeCommodityMetaData() {

        List<MetaDataResponse> metaDataResponseList = new ArrayList<>();

        List<MetaDataItem> unitItems = new ArrayList<>();
        unitItems.add(MetaDataItem.build(Unit.NUMBER.name()));
        unitItems.add(MetaDataItem.build(Unit.KILOGRAM.name()));
        unitItems.add(MetaDataItem.build(Unit.METER.name()));
        unitItems.add(MetaDataItem.build(Unit.TONNE.name()));
        unitItems.add(MetaDataItem.build(Unit.GRAM.name()));
        unitItems.add(MetaDataItem.build(Unit.MESQAL.name()));
        unitItems.add(MetaDataItem.build(Unit.SQUARE_METER.name()));
        unitItems.add(MetaDataItem.build(Unit.CENTIMETRE.name()));
        unitItems.add(MetaDataItem.build(Unit.LITRE.name()));
        unitItems.add(MetaDataItem.build(Unit.OTHER.name()));
        metaDataResponseList.add(new MetaDataResponse("Unit", unitItems));

        return metaDataResponseList;

    }

}

