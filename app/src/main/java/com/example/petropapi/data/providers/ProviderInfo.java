package com.example.petropapi.data.providers;

public class ProviderInfo {
    private final String name;
    private final String category;
    private final String coverage;
    private final String dataType;
    private final String bestUseCase;
    private final String licenseType;

    public ProviderInfo(String name,
                        String category,
                        String coverage,
                        String dataType,
                        String bestUseCase,
                        String licenseType) {
        this.name = name;
        this.category = category;
        this.coverage = coverage;
        this.dataType = dataType;
        this.bestUseCase = bestUseCase;
        this.licenseType = licenseType;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getCoverage() {
        return coverage;
    }

    public String getDataType() {
        return dataType;
    }

    public String getBestUseCase() {
        return bestUseCase;
    }

    public String getLicenseType() {
        return licenseType;
    }
}
