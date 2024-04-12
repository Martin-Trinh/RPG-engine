package com.trinhdin.rpg.model.GameEntity.Ability;

import com.trinhdin.rpg.model.GameEntity.Character.Character;

public class AttackAbility extends Ability{
    private int damage;
    private enum AttackType{
        PHYSICAL,
        MAGICAL
    }
    private AttackType type;
    public AttackAbility(String name, int cost, int cooldown, int damage, AttackType type) {
        super(name, cost, cooldown);
        this.damage = damage;
        this.type = type;
    }
    @Override
    public void use(Character caster, Character target) {
        if(caster.getCurrentMana() < cost) {
            System.out.println("Not enough mana");
        }else{
            target.setCurrentHealth(target.getCurrentHealth() - calculateDamage(caster, target));
            caster.setCurrentMana(caster.getCurrentMana() - cost);
        }
    }

    private int calculateDamage(Character caster, Character target){
        if(type == AttackType.PHYSICAL){
            return caster.getStat().getStrength() * damage - target.getStat().getArmor();
        }else{
            return caster.getStat().getIntelligence() * damage - target.getStat().getMagicArmor();
        }
    }
}
