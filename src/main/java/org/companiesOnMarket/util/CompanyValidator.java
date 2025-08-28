package org.companiesOnMarket.util;

import org.companiesOnMarket.entity.Company;
import org.companiesOnMarket.error.ValidationException;

public class CompanyValidator {

    private static final String emailValidationRegex = "^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$";

    public static void validateForCreate(Company company) {
        String companyName = company.getName();
        String companyCountry = company.getCountry();
        String companySymbol = company.getSymbol();

        if (companyName == null || companyName.isBlank()) {
            throw new ValidationException("Company name is required");
        }

        if (companyCountry == null || companyCountry.isBlank()) {
            throw new ValidationException("Company country is required");
        } else if (companyCountry.length() != 2) {
            throw new ValidationException("Country must be 2 characters (ISO code)");
        }

        if (companySymbol == null || companySymbol.isBlank()) {
            throw new ValidationException("Company symbol is required");
        }

        validateOptionalInputs(company);
    }

    public static void validateForUpdate(Company company)
    {
        if (company.getName() != null && company.getName().isBlank())
        {
            throw new ValidationException("Invalid company name");
        }

        if (company.getCountry() != null && company.getCountry().isBlank())
        {
            throw new ValidationException("Invalid company country");
        }

        if (company.getSymbol() != null && company.getSymbol().isBlank())
        {
            throw new ValidationException("Invalid company symbol");
        }

        validateOptionalInputs(company);
    }

    public static void validateOptionalInputs(Company company)
    {
        String companyEmail = company.getEmail();
        String companyWebsite = company.getWebsite();

        if (companyEmail != null && !company.getEmail().matches(emailValidationRegex))
        {
            throw new ValidationException("Company email is invalid");
        }

        if (companyWebsite != null && companyWebsite.isBlank())
        {
            throw new ValidationException("Company website is invalid");
        }
    }
}
