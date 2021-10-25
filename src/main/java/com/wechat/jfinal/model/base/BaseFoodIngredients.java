package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseFoodIngredients<M extends BaseFoodIngredients<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}

	public java.lang.String getName() {
		return getStr("name");
	}

	public M setArea(java.lang.String area) {
		set("area", area);
		return (M)this;
	}

	public java.lang.String getArea() {
		return getStr("area");
	}

	public M setEdible(java.lang.String edible) {
		set("edible", edible);
		return (M)this;
	}

	public java.lang.String getEdible() {
		return getStr("edible");
	}

	public M setEnergy(java.lang.String energy) {
		set("energy", energy);
		return (M)this;
	}

	public java.lang.String getEnergy() {
		return getStr("energy");
	}

	public M setWater(java.lang.String water) {
		set("water", water);
		return (M)this;
	}

	public java.lang.String getWater() {
		return getStr("water");
	}

	public M setProtein(java.lang.String protein) {
		set("protein", protein);
		return (M)this;
	}

	public java.lang.String getProtein() {
		return getStr("protein");
	}

	public M setFat(java.lang.String fat) {
		set("fat", fat);
		return (M)this;
	}

	public java.lang.String getFat() {
		return getStr("fat");
	}

	public M setDietaryFiber(java.lang.String dietaryFiber) {
		set("dietary_fiber", dietaryFiber);
		return (M)this;
	}

	public java.lang.String getDietaryFiber() {
		return getStr("dietary_fiber");
	}

	public M setCarbohydrate(java.lang.String carbohydrate) {
		set("carbohydrate", carbohydrate);
		return (M)this;
	}

	public java.lang.String getCarbohydrate() {
		return getStr("carbohydrate");
	}

	public M setRetinolEquivalent(java.lang.String retinolEquivalent) {
		set("retinol_equivalent", retinolEquivalent);
		return (M)this;
	}

	public java.lang.String getRetinolEquivalent() {
		return getStr("retinol_equivalent");
	}

	public M setVb1(java.lang.String vb1) {
		set("vb1", vb1);
		return (M)this;
	}

	public java.lang.String getVb1() {
		return getStr("vb1");
	}

	public M setVb2(java.lang.String vb2) {
		set("vb2", vb2);
		return (M)this;
	}

	public java.lang.String getVb2() {
		return getStr("vb2");
	}

	public M setVpp(java.lang.String vpp) {
		set("vpp", vpp);
		return (M)this;
	}

	public java.lang.String getVpp() {
		return getStr("vpp");
	}

	public M setVe(java.lang.String ve) {
		set("ve", ve);
		return (M)this;
	}

	public java.lang.String getVe() {
		return getStr("ve");
	}

	public M setNa(java.lang.String na) {
		set("na", na);
		return (M)this;
	}

	public java.lang.String getNa() {
		return getStr("na");
	}

	public M setCa(java.lang.String ca) {
		set("ca", ca);
		return (M)this;
	}

	public java.lang.String getCa() {
		return getStr("ca");
	}

	public M setFe(java.lang.String fe) {
		set("fe", fe);
		return (M)this;
	}

	public java.lang.String getFe() {
		return getStr("fe");
	}

	public M setCategory(java.lang.String category) {
		set("category", category);
		return (M)this;
	}

	public java.lang.String getCategory() {
		return getStr("category");
	}

	public M setVc(java.lang.String vc) {
		set("vc", vc);
		return (M)this;
	}

	public java.lang.String getVc() {
		return getStr("vc");
	}

	public M setLabel(java.lang.String label) {
		set("label", label);
		return (M)this;
	}

	public java.lang.String getLabel() {
		return getStr("label");
	}

	public M setCholesterol(java.lang.String cholesterol) {
		set("cholesterol", cholesterol);
		return (M)this;
	}

	public java.lang.String getCholesterol() {
		return getStr("cholesterol");
	}

}
