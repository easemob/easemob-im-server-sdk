/*
 * EMService
 * Easemob Rest API
 *
 * The version of the OpenAPI document: 1.0.0
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.easemob.im.api.model;

import java.util.Objects;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.easemob.im.JSON;

/**
 * EMCreateGroup
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-09-19T17:22:04.272445+08:00[Asia/Shanghai]")
public class EMCreateGroup {
    public static final String SERIALIZED_NAME_GROUPID = "groupid";
    @SerializedName(SERIALIZED_NAME_GROUPID)
    private String groupid;

    public static final String SERIALIZED_NAME_GROUPNAME = "groupname";
    @SerializedName(SERIALIZED_NAME_GROUPNAME)
    private String groupname;

    public static final String SERIALIZED_NAME_AVATAR = "avatar";
    @SerializedName(SERIALIZED_NAME_AVATAR)
    private String avatar;

    public static final String SERIALIZED_NAME_DESCRIPTION = "description";
    @SerializedName(SERIALIZED_NAME_DESCRIPTION)
    private String description;

    public static final String SERIALIZED_NAME_PUBLIC = "public";
    @SerializedName(SERIALIZED_NAME_PUBLIC)
    private Boolean _public;

    public static final String SERIALIZED_NAME_SCALE = "scale";
    @SerializedName(SERIALIZED_NAME_SCALE)
    private String scale;

    public static final String SERIALIZED_NAME_MAXUSERS = "maxusers";
    @SerializedName(SERIALIZED_NAME_MAXUSERS)
    private Integer maxusers;

    public static final String SERIALIZED_NAME_ALLOWINVITES = "allowinvites";
    @SerializedName(SERIALIZED_NAME_ALLOWINVITES)
    private Boolean allowinvites;

    public static final String SERIALIZED_NAME_MEMBERSONLY = "membersonly";
    @SerializedName(SERIALIZED_NAME_MEMBERSONLY)
    private Boolean membersonly;

    public static final String SERIALIZED_NAME_INVITE_NEED_CONFIRM = "invite_need_confirm";
    @SerializedName(SERIALIZED_NAME_INVITE_NEED_CONFIRM)
    private Boolean inviteNeedConfirm;

    public static final String SERIALIZED_NAME_OWNER = "owner";
    @SerializedName(SERIALIZED_NAME_OWNER)
    private String owner;

    public static final String SERIALIZED_NAME_MEMBERS = "members";
    @SerializedName(SERIALIZED_NAME_MEMBERS)
    private List<String> members;

    public static final String SERIALIZED_NAME_CUSTOM = "custom";
    @SerializedName(SERIALIZED_NAME_CUSTOM)
    private String custom;

    public EMCreateGroup() {
    }

    public EMCreateGroup groupid(String groupid) {

        this.groupid = groupid;
        return this;
    }

    /**
     * 自定义群组id。如需该功能，请联系环信商务
     *
     * @return groupid
     **/
    @javax.annotation.Nullable
    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public EMCreateGroup groupname(String groupname) {

        this.groupname = groupname;
        return this;
    }

    /**
     * 群组名称，最大长度为 128 字符
     *
     * @return groupname
     **/
    @javax.annotation.Nullable
    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public EMCreateGroup avatar(String avatar) {

        this.avatar = avatar;
        return this;
    }

    /**
     * 群组头像的 URL，最大长度为 1024 字符
     *
     * @return avatar
     **/
    @javax.annotation.Nullable
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public EMCreateGroup description(String description) {

        this.description = description;
        return this;
    }

    /**
     * 群组描述，最大长度为 512 字符
     *
     * @return description
     **/
    @javax.annotation.Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EMCreateGroup _public(Boolean _public) {

        this._public = _public;
        return this;
    }

    /**
     * 是否是公开群。公开群可以被搜索到，用户可以申请加入公开群；私有群无法被搜索到，因此需要群主或群管理员添加，用户才可以加入。 - true：公开群； - false：私有群
     *
     * @return _public
     **/
    @javax.annotation.Nonnull
    public Boolean getPublic() {
        return _public;
    }

    public void setPublic(Boolean _public) {
        this._public = _public;
    }

    public EMCreateGroup scale(String scale) {

        this.scale = scale;
        return this;
    }

    /**
     * 群组规模，取决于群成员总数 maxusers 参数。 - （默认）normal：普通群，即群成员总数不超过 3000。 - large：大型群，群成员总数超过 3000 注意： - 创建大型群时，该参数必传； - 大型群不支持离线推送。仅旗舰版支持创建大型群，如需该功能，请联系环信商务
     *
     * @return scale
     **/
    @javax.annotation.Nullable
    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public EMCreateGroup maxusers(Integer maxusers) {

        this.maxusers = maxusers;
        return this;
    }

    /**
     * 群组最大成员数（包括群主）。对于普通群，该参数的默认值为 200，大型群为 1000。不同套餐支持的人数上限不同，详见 产品价格
     *
     * @return maxusers
     **/
    @javax.annotation.Nullable
    public Integer getMaxusers() {
        return maxusers;
    }

    public void setMaxusers(Integer maxusers) {
        this.maxusers = maxusers;
    }

    public EMCreateGroup allowinvites(Boolean allowinvites) {

        this.allowinvites = allowinvites;
        return this;
    }

    /**
     * 是否允许群成员邀请用户加入群组： - true：群成员可拉人入群; - （默认）false：只有群主或者管理员才可以拉人入群。 注：该参数仅对私有群有效，因为公开群不允许群成员邀请其他用户入群
     *
     * @return allowinvites
     **/
    @javax.annotation.Nullable
    public Boolean getAllowinvites() {
        return allowinvites;
    }

    public void setAllowinvites(Boolean allowinvites) {
        this.allowinvites = allowinvites;
    }

    public EMCreateGroup membersonly(Boolean membersonly) {

        this.membersonly = membersonly;
        return this;
    }

    /**
     * 用户申请入群是否需要群主或者群管理员审批。 - true：需要； - （默认）false：不需要，用户直接进群。 该参数仅对公开群生效，因为对于私有群，用户无法申请加入群组，只能通过群成员邀请加入群
     *
     * @return membersonly
     **/
    @javax.annotation.Nullable
    public Boolean getMembersonly() {
        return membersonly;
    }

    public void setMembersonly(Boolean membersonly) {
        this.membersonly = membersonly;
    }

    public EMCreateGroup inviteNeedConfirm(Boolean inviteNeedConfirm) {

        this.inviteNeedConfirm = inviteNeedConfirm;
        return this;
    }

    /**
     * 邀请用户入群时是否需要被邀用户同意。 - （默认）true：是； - false：否
     *
     * @return inviteNeedConfirm
     **/
    @javax.annotation.Nullable
    public Boolean getInviteNeedConfirm() {
        return inviteNeedConfirm;
    }

    public void setInviteNeedConfirm(Boolean inviteNeedConfirm) {
        this.inviteNeedConfirm = inviteNeedConfirm;
    }

    public EMCreateGroup owner(String owner) {

        this.owner = owner;
        return this;
    }

    /**
     * 群主的用户 ID
     *
     * @return owner
     **/
    @javax.annotation.Nonnull
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public EMCreateGroup members(List<String> members) {

        this.members = members;
        return this;
    }

    public EMCreateGroup addMembersItem(String membersItem) {
        if (this.members == null) {
            this.members = new ArrayList<>();
        }
        this.members.add(membersItem);
        return this;
    }

    /**
     * 群成员的用户 ID 数组，不包含群主的用户 ID。该数组可包含的元素数量不超过 maxusers 的值
     *
     * @return members
     **/
    @javax.annotation.Nullable
    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public EMCreateGroup custom(String custom) {

        this.custom = custom;
        return this;
    }

    /**
     * 群组扩展信息，例如可以给群组添加业务相关的标记，不要超过 1,024 字符
     *
     * @return custom
     **/
    @javax.annotation.Nullable
    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EMCreateGroup createGroup = (EMCreateGroup) o;
        return Objects.equals(this.groupid, createGroup.groupid) &&
                Objects.equals(this.groupname, createGroup.groupname) &&
                Objects.equals(this.avatar, createGroup.avatar) &&
                Objects.equals(this.description, createGroup.description) &&
                Objects.equals(this._public, createGroup._public) &&
                Objects.equals(this.scale, createGroup.scale) &&
                Objects.equals(this.maxusers, createGroup.maxusers) &&
                Objects.equals(this.allowinvites, createGroup.allowinvites) &&
                Objects.equals(this.membersonly, createGroup.membersonly) &&
                Objects.equals(this.inviteNeedConfirm, createGroup.inviteNeedConfirm) &&
                Objects.equals(this.owner, createGroup.owner) &&
                Objects.equals(this.members, createGroup.members) &&
                Objects.equals(this.custom, createGroup.custom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupid, groupname, avatar, description, _public, scale, maxusers,
                allowinvites, membersonly, inviteNeedConfirm, owner, members, custom);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class EMCreateGroup {\n");
        sb.append("    groupid: ").append(toIndentedString(groupid)).append("\n");
        sb.append("    groupname: ").append(toIndentedString(groupname)).append("\n");
        sb.append("    avatar: ").append(toIndentedString(avatar)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    _public: ").append(toIndentedString(_public)).append("\n");
        sb.append("    scale: ").append(toIndentedString(scale)).append("\n");
        sb.append("    maxusers: ").append(toIndentedString(maxusers)).append("\n");
        sb.append("    allowinvites: ").append(toIndentedString(allowinvites)).append("\n");
        sb.append("    membersonly: ").append(toIndentedString(membersonly)).append("\n");
        sb.append("    inviteNeedConfirm: ").append(toIndentedString(inviteNeedConfirm))
                .append("\n");
        sb.append("    owner: ").append(toIndentedString(owner)).append("\n");
        sb.append("    members: ").append(toIndentedString(members)).append("\n");
        sb.append("    custom: ").append(toIndentedString(custom)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

    public static HashSet<String> openapiFields;
    public static HashSet<String> openapiRequiredFields;

    static {
        // a set of all properties/fields (JSON key names)
        openapiFields = new HashSet<String>();
        openapiFields.add("groupid");
        openapiFields.add("groupname");
        openapiFields.add("avatar");
        openapiFields.add("description");
        openapiFields.add("public");
        openapiFields.add("scale");
        openapiFields.add("maxusers");
        openapiFields.add("allowinvites");
        openapiFields.add("membersonly");
        openapiFields.add("invite_need_confirm");
        openapiFields.add("owner");
        openapiFields.add("members");
        openapiFields.add("custom");

        // a set of required properties/fields (JSON key names)
        openapiRequiredFields = new HashSet<String>();
        openapiRequiredFields.add("public");
        openapiRequiredFields.add("owner");
    }

    /**
     * Validates the JSON Element and throws an exception if issues found
     *
     * @param jsonElement JSON Element
     * @throws IOException if the JSON Element is invalid with respect to EMCreateGroup
     */
    public static void validateJsonElement(JsonElement jsonElement) throws IOException {
        if (jsonElement == null) {
            if (!EMCreateGroup.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
                throw new IllegalArgumentException(String.format(
                        "The required field(s) %s in EMCreateGroup is not found in the empty JSON string",
                        EMCreateGroup.openapiRequiredFields.toString()));
            }
        }

        Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
        // check to see if the JSON string contains additional fields
        for (Map.Entry<String, JsonElement> entry : entries) {
            if (!EMCreateGroup.openapiFields.contains(entry.getKey())) {
                throw new IllegalArgumentException(String.format(
                        "The field `%s` in the JSON string is not defined in the `EMCreateGroup` properties. JSON: %s",
                        entry.getKey(), jsonElement.toString()));
            }
        }

        // check to make sure all required properties/fields are present in the JSON string
        for (String requiredField : EMCreateGroup.openapiRequiredFields) {
            if (jsonElement.getAsJsonObject().get(requiredField) == null) {
                throw new IllegalArgumentException(
                        String.format("The required field `%s` is not found in the JSON string: %s",
                                requiredField, jsonElement.toString()));
            }
        }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
        if ((jsonObj.get("groupid") != null && !jsonObj.get("groupid").isJsonNull())
                && !jsonObj.get("groupid").isJsonPrimitive()) {
            throw new IllegalArgumentException(String.format(
                    "Expected the field `groupid` to be a primitive type in the JSON string but got `%s`",
                    jsonObj.get("groupid").toString()));
        }
        if ((jsonObj.get("groupname") != null && !jsonObj.get("groupname").isJsonNull())
                && !jsonObj.get("groupname").isJsonPrimitive()) {
            throw new IllegalArgumentException(String.format(
                    "Expected the field `groupname` to be a primitive type in the JSON string but got `%s`",
                    jsonObj.get("groupname").toString()));
        }
        if ((jsonObj.get("avatar") != null && !jsonObj.get("avatar").isJsonNull()) && !jsonObj.get(
                "avatar").isJsonPrimitive()) {
            throw new IllegalArgumentException(String.format(
                    "Expected the field `avatar` to be a primitive type in the JSON string but got `%s`",
                    jsonObj.get("avatar").toString()));
        }
        if ((jsonObj.get("description") != null && !jsonObj.get("description").isJsonNull())
                && !jsonObj.get("description").isJsonPrimitive()) {
            throw new IllegalArgumentException(String.format(
                    "Expected the field `description` to be a primitive type in the JSON string but got `%s`",
                    jsonObj.get("description").toString()));
        }
        if ((jsonObj.get("scale") != null && !jsonObj.get("scale").isJsonNull()) && !jsonObj.get(
                "scale").isJsonPrimitive()) {
            throw new IllegalArgumentException(String.format(
                    "Expected the field `scale` to be a primitive type in the JSON string but got `%s`",
                    jsonObj.get("scale").toString()));
        }
        if (!jsonObj.get("owner").isJsonPrimitive()) {
            throw new IllegalArgumentException(String.format(
                    "Expected the field `owner` to be a primitive type in the JSON string but got `%s`",
                    jsonObj.get("owner").toString()));
        }
        // ensure the optional json data is an array if present
        if (jsonObj.get("members") != null && !jsonObj.get("members").isJsonNull() && !jsonObj.get(
                "members").isJsonArray()) {
            throw new IllegalArgumentException(String.format(
                    "Expected the field `members` to be an array in the JSON string but got `%s`",
                    jsonObj.get("members").toString()));
        }
        if ((jsonObj.get("custom") != null && !jsonObj.get("custom").isJsonNull()) && !jsonObj.get(
                "custom").isJsonPrimitive()) {
            throw new IllegalArgumentException(String.format(
                    "Expected the field `custom` to be a primitive type in the JSON string but got `%s`",
                    jsonObj.get("custom").toString()));
        }
    }

    public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
        @SuppressWarnings("unchecked")
        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            if (!EMCreateGroup.class.isAssignableFrom(type.getRawType())) {
                return null; // this class only serializes 'EMCreateGroup' and its subtypes
            }
            final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
            final TypeAdapter<EMCreateGroup> thisAdapter
                    = gson.getDelegateAdapter(this, TypeToken.get(EMCreateGroup.class));

            return (TypeAdapter<T>) new TypeAdapter<EMCreateGroup>() {
                @Override
                public void write(JsonWriter out, EMCreateGroup value) throws IOException {
                    JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
                    elementAdapter.write(out, obj);
                }

                @Override
                public EMCreateGroup read(JsonReader in) throws IOException {
                    JsonElement jsonElement = elementAdapter.read(in);
                    validateJsonElement(jsonElement);
                    return thisAdapter.fromJsonTree(jsonElement);
                }

            }.nullSafe();
        }
    }

    /**
     * Create an instance of EMCreateGroup given an JSON string
     *
     * @param jsonString JSON string
     * @return An instance of EMCreateGroup
     * @throws IOException if the JSON string is invalid with respect to EMCreateGroup
     */
    public static EMCreateGroup fromJson(String jsonString) throws IOException {
        return JSON.getGson().fromJson(jsonString, EMCreateGroup.class);
    }

    /**
     * Convert an instance of EMCreateGroup to an JSON string
     *
     * @return JSON string
     */
    public String toJson() {
        return JSON.getGson().toJson(this);
    }
}

