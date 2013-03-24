package com.udinic.accounts_auth_example.dto;

import net.iharder.Base64;

import java.io.IOException;
import java.util.UUID;

/**
 * User: alexv
 * Date: 9/6/11
 * Time: 8:46 AM
 */
public class InstallationDetailsDto {
    private String version_code;
    private String c2dmReceiverId;
    private String gcmId;
    private String installationId;
    private Platform platform;
    private String pnsToken;
    private String countryCode;
    private String osVersion;
    private String udid;
    private String userSettings;

    public static InstallationDetailsDto random() {
        return new InstallationDetailsDto(randomBase64UUID());
    }

    public InstallationDetailsDto() {
    }

    public InstallationDetailsDto(String installationId, String c2dm) {
        this.installationId = installationId;
        this.c2dmReceiverId = c2dm;
    }

    public InstallationDetailsDto(String installationId) {
        this.installationId = installationId;
    }

    public String getVersion_code() {
        return version_code;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public String getC2dmReceiverId() {
        return c2dmReceiverId;
    }

    public void setC2dmReceiverId(String c2dmReceiverId) {
        this.c2dmReceiverId = c2dmReceiverId;
    }

    public String getGcmId() {
        return gcmId;
    }

    public void setGcmId(String gcmId) {
        this.gcmId = gcmId;
    }

    public String getInstallationId() {
        return installationId;
    }

    public void setInstallationId(String installationId) {
        this.installationId = installationId;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public String getPnsToken() {
        return pnsToken;
    }

    public void setPnsToken(String pnsToken) {
        this.pnsToken = pnsToken;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InstallationDetailsDto that = (InstallationDetailsDto) o;

        if (installationId != null ? !installationId.equals(that.installationId) : that.installationId != null)
            return false;

        return true;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    @Override
    public int hashCode() {
        return installationId != null ? installationId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "InstallationDetailsDto{" +
            "version_code='" + version_code + '\'' +
            ", c2dmReceiverId='" + c2dmReceiverId + '\'' +
            ", gcmId='" + gcmId + '\'' +
            ", installationId='" + installationId + '\'' +
            ", platform=" + platform +
            ", pnsToken='" + pnsToken + '\'' +
            '}';
    }

    public String getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(String userSettings) {
        this.userSettings = userSettings;
    }

    public static String randomBase64UUID() {
        try {
            return Base64.encodeBytes(asByteArray(UUID.randomUUID()), Base64.URL_SAFE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static byte[] asByteArray(UUID uuid) {
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        byte[] buffer = new byte[16];

        for (int i = 0; i < 8; i++) {
            buffer[i] = (byte) (msb >>> 8 * (7 - i));
        }
        for (int i = 8; i < 16; i++) {
            buffer[i] = (byte) (lsb >>> 8 * (7 - i));
        }

        return buffer;

    }


}
