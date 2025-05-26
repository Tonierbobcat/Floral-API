package com.loficostudios.floralcraftapi.profile.components.attributes;

import com.loficostudios.floralcraftapi.profile.PlayerProfile;
import com.loficostudios.floralcraftapi.profile.components.base.ProfileComponent;
import com.loficostudios.floralcraftapi.utils.interfaces.Mappable;

import java.util.HashMap;
import java.util.Map;

public class PlayerAttributes extends ProfileComponent implements Mappable<Object> {

    private final AttributeMap map;

    private int currentAttributePoints;

    public PlayerAttributes(PlayerProfile profile) {
        super(profile);
        this.map = new AttributeMap(profile);
    }

    //load
    @SuppressWarnings("unchecked")
    public PlayerAttributes(PlayerProfile profile, Map<String,Object> data) {
        super(profile);
        this.map = new AttributeMap(profile);
        if (data != null) {

            Object attributePointsObj = data.get("attribute-points");
            if (attributePointsObj instanceof Integer) {
                this.currentAttributePoints = (Integer) attributePointsObj;
            }

            if (data.get("attributes") instanceof Map) {
                Map<String, Integer> attributes = (Map<String, Integer>) data.get("attributes");
                for (Map.Entry<String, Integer> entry : attributes.entrySet()) {
                    String attributeName = entry.getKey();
                    Integer baseValue = entry.getValue();

                    Attribute attribute = Attributes.valueOf(attributeName);
                    AttributeMap.AttributeInstance instance = getAttribute(attribute);
                    if (instance != null) {
                        instance.setBase(baseValue, false);
                    }
                }
            }
        }
    }

    public AttributeMap.AttributeInstance getAttribute(Attribute attribute) {
        return map.getAttribute(attribute);
    }

    public int getAvailablePoints() {
        return currentAttributePoints;
    }

    public void setAvailablePoints(int v) {
        this.currentAttributePoints = v;
    }

    public void reset() {
        for (Attribute attribute : Attributes.values()) {
            var instance = getAttribute(attribute);
            if (instance == null)
                continue;
            instance.setBase(0);
        }
    }

    public void removePointFromAttribute(Attribute attribute) {
        setAvailablePoints(currentAttributePoints + 1);
        var instance = getAttribute(attribute);
        instance.setBase(Math.max(0, instance.getBase() - 1));
    }

    public void addPointToAttribute(Attribute attribute) {
        setAvailablePoints(Math.max(0, currentAttributePoints - 1));
        var instance = getAttribute(attribute);
        instance.setBase(instance.getBase() + 1);
    }

    @Override
    public Map<String, Object> toMap() { // save map
        var result = new HashMap<String, Object>();

        result.put("attribute-points", currentAttributePoints);
        result.put("attributes", getAttriubteMap());

        return result;
    }

    private Map<String, Integer> getAttriubteMap() {
        var result = new HashMap<String, Integer>();
        for (Attribute attribute : Attributes.values()) {
            var instance = getAttribute(attribute);
            if (instance == null)
                continue;;
            result.put(attribute.getName(), instance.getBase());
        }
        return result;
    }

    public void update() {
        for (Attribute value : Attributes.values()) {
            var instance = map.getAttribute(value);
            if (instance != null)
                instance.update();
        }
    }
}
