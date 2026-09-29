-- =====================================================================
-- Starter content for the public site and back office.
-- Mirrors the frontend mock data (src/app/core/mock-api/mock-db.ts).
-- All names, numbers and addresses are placeholders: replace them from
-- the back office before going live.
-- =====================================================================

-- ---------------------------------------------------------------------
-- Offices (areas_served is comma-separated)
-- ---------------------------------------------------------------------
INSERT INTO office_location
    (id, city, slug, hero_heading, intro, areas_served, address_line, phone, email, office_hours, head_office, active, sort_order)
VALUES
    (1, 'Hyderabad', 'hyderabad',
     'Audit, tax and compliance for Hyderabad businesses',
     'From GST returns for a Kondapur retailer to statutory audit for a Financial District subsidiary, our Hyderabad team handles the numbers so founders and finance heads can focus on running the business.',
     'Gachibowli,Madhapur,HITEC City,Financial District,Kondapur,Banjara Hills',
     '4th Floor, Example Towers, Road No. 2, Madhapur, Hyderabad 500081',
     '+91 40 0000 0000', 'hyderabad@yourfirm.in', 'Mon-Sat, 9:30 am - 6:30 pm', TRUE, TRUE, 1),
    (2, 'Bengaluru', 'bengaluru',
     'Audit, tax and compliance for Bengaluru businesses',
     'Our Bengaluru team works with startups, SaaS companies and GCCs on audit, direct tax, GST and company law.',
     'Koramangala,Indiranagar,HSR Layout,Whitefield,Outer Ring Road',
     '2nd Floor, Sample House, 80 Feet Road, Koramangala, Bengaluru 560034',
     '+91 80 0000 0000', 'bengaluru@yourfirm.in', 'Mon-Sat, 9:30 am - 6:30 pm', FALSE, TRUE, 2);

-- ---------------------------------------------------------------------
-- Services
-- ---------------------------------------------------------------------
INSERT INTO service_category (id, name, slug, sort_order) VALUES
    (1, 'Assurance', 'assurance', 1),
    (2, 'Taxation', 'taxation', 2),
    (3, 'Company Law', 'company-law', 3),
    (4, 'Advisory & Outsourcing', 'advisory', 4);

INSERT INTO firm_service (id, category_id, title, slug, summary, body, icon, featured, active, sort_order) VALUES
    (1, 1, 'Statutory Audit', 'statutory-audit',
     'Audits under the Companies Act, 2013 planned around your year-end, with clear management letters.',
     'Audits under the Companies Act, 2013 planned around your year-end, with clear management letters.',
     'audit', TRUE, TRUE, 1),
    (2, 1, 'Internal Audit', 'internal-audit',
     'Risk-based reviews of your processes, controls and fraud exposure, reported to the board.',
     'Risk-based reviews of your processes, controls and fraud exposure, reported to the board.',
     'internal-audit', TRUE, TRUE, 2),
    (3, 2, 'Income Tax & Consulting', 'income-tax',
     'Return filing, tax planning, assessments and appeals for individuals and companies.',
     'Return filing, tax planning, assessments and appeals for individuals and companies.',
     'tax', TRUE, TRUE, 3),
    (4, 2, 'GST Registration & Returns', 'gst-registration',
     'GST registration, monthly and annual returns, reconciliations and notice replies.',
     'From the first registration to GSTR-9 annual returns, we keep your GST filings on time and reconcile input tax credit against GSTR-2B every month.',
     'gst', TRUE, TRUE, 4),
    (5, 2, 'Transfer Pricing & International Tax', 'transfer-pricing',
     'Benchmarking studies, Form 3CEB, DTAA advice and cross-border structuring.',
     'Benchmarking studies, Form 3CEB, DTAA advice and cross-border structuring.',
     'globe', TRUE, TRUE, 5),
    (6, 3, 'Company Registration', 'company-registration',
     'Private limited, LLP and subsidiary incorporation, with PAN, TAN and GST in one go.',
     'Private limited, LLP and subsidiary incorporation, with PAN, TAN and GST in one go.',
     'building', TRUE, TRUE, 6),
    (7, 4, 'Virtual CFO Services', 'virtual-cfo',
     'Monthly MIS, budgeting, cash-flow forecasting and investor reporting without a full-time CFO.',
     'Monthly MIS, budgeting, cash-flow forecasting and investor reporting without a full-time CFO.',
     'chart', TRUE, TRUE, 7),
    (8, 4, 'Payroll Services', 'payroll',
     'Salary processing, payslips, TDS on salary, PF, ESI and professional tax filings.',
     'Salary processing, payslips, TDS on salary, PF, ESI and professional tax filings.',
     'payroll', TRUE, TRUE, 8);

INSERT INTO service_highlight (service_id, text, sort_order) VALUES
    (1, 'Statutory audit under the Companies Act, 2013', 1),
    (1, 'Tax audit under section 44AB and Form 3CD', 2),
    (1, 'Management letter on control gaps and adjustments', 3),
    (1, 'Coordination with your AGM and ROC filing timelines', 4),
    (2, 'Risk assessment and annual internal audit plan', 1),
    (2, 'Process and internal financial controls testing', 2),
    (2, 'Quarterly reports to the audit committee', 3),
    (3, 'Income tax returns for individuals, firms and companies', 1),
    (3, 'Advance tax and TDS compliance', 2),
    (3, 'Scrutiny assessments, rectifications and appeals', 3),
    (4, 'New GST registration, amendments and cancellations', 1),
    (4, 'GSTR-1, GSTR-3B and GSTR-9 / 9C filings', 2),
    (4, 'Monthly input tax credit reconciliation with GSTR-2B', 3),
    (4, 'Replies to GST notices and audit queries', 4),
    (5, 'Transfer pricing documentation and benchmarking', 1),
    (5, 'Form 3CEB certification', 2),
    (5, 'DTAA and withholding tax advice', 3),
    (6, 'Private limited, LLP and Indian subsidiary incorporation', 1),
    (6, 'PAN, TAN, GST and bank account set-up', 2),
    (6, 'First-year board meetings and ROC filings', 3),
    (7, 'Monthly MIS and management accounts', 1),
    (7, 'Budgets and cash-flow forecasts', 2),
    (7, 'Board and investor reporting', 3),
    (8, 'Monthly salary processing and payslips', 1),
    (8, 'TDS on salary, Form 16 and quarterly returns', 2),
    (8, 'PF, ESI and professional tax filings', 3);

-- ---------------------------------------------------------------------
-- Industries
-- ---------------------------------------------------------------------
INSERT INTO industry (id, name, slug, summary, icon, sort_order) VALUES
    (1, 'Information Technology', 'information-technology', 'SaaS, IT services and GCCs', 'chip', 1),
    (2, 'Pharmaceutical', 'pharmaceutical', 'Manufacturers, CROs and distributors', 'flask', 2),
    (3, 'Real Estate', 'real-estate', 'Developers, RERA and JDAs', 'building', 3),
    (4, 'Retail', 'retail', 'Multi-store and franchise retail', 'store', 4),
    (5, 'E-commerce', 'e-commerce', 'Marketplace sellers and D2C brands', 'cart', 5),
    (6, 'Automobile', 'automobile', 'Dealerships and component makers', 'car', 6),
    (7, 'Hotels & Restaurants', 'hotels-restaurants', 'Hotels, QSRs and cloud kitchens', 'cup', 7),
    (8, 'Media & Entertainment', 'media-entertainment', 'Production houses and studios', 'film', 8);

-- ---------------------------------------------------------------------
-- FAQs (location_id NULL = every city; service_id NULL = general)
-- ---------------------------------------------------------------------
INSERT INTO faq (id, question, answer, location_id, service_id, active, sort_order) VALUES
    (1, 'Which areas of Hyderabad do you serve?',
     'Our office is in Madhapur and we regularly work with clients in Gachibowli, HITEC City, the Financial District, Kondapur and Banjara Hills. Most work is done online, so we also support clients elsewhere in Telangana.',
     1, NULL, TRUE, 1),
    (2, 'Can you help with GST registration in Hyderabad?',
     'Yes. We prepare the application, upload documents on the GST portal and follow up until the GSTIN is issued. We can then take over your monthly returns.',
     1, NULL, TRUE, 2),
    (3, 'How do I book a consultation?',
     'Use the enquiry form on this page or call the office. A chartered accountant will call you back within one working day to understand your requirement.',
     NULL, NULL, TRUE, 3),
    (4, 'Do you work with startups?',
     'Yes. We help startups with incorporation, DPIIT recognition, bookkeeping, payroll and investor reporting, and scale the engagement as the company grows.',
     NULL, NULL, TRUE, 4),
    (5, 'Can a chartered accountant file my income tax return?',
     'Yes. We prepare and file returns for salaried individuals, professionals, firms and companies, and handle any notices that follow.',
     NULL, NULL, TRUE, 5),
    (6, 'Why hire a chartered accountant instead of doing compliance in-house?',
     'A CA keeps track of changing laws and due dates, reduces penalties and interest, and gives you an independent view of your numbers that banks and investors rely on.',
     NULL, NULL, TRUE, 6),
    (7, 'Can you help with GST registration in Hyderabad?',
     'Yes. We prepare the application, upload documents on the GST portal and follow up until the GSTIN is issued. We can then take over your monthly returns.',
     NULL, 4, TRUE, 7),
    (8, 'What documents do I need for registration?',
     'PAN of the business, proof of address for the place of business, identity and address proof of the promoters, and bank account details.',
     NULL, 4, TRUE, 8);

-- ---------------------------------------------------------------------
-- Testimonials
-- ---------------------------------------------------------------------
INSERT INTO testimonial (id, author_name, author_title, quote, rating, active, sort_order) VALUES
    (1, 'Sample client', 'Director, SaaS company, Madhapur',
     'Example: the team closed our audit two weeks ahead of the AGM and explained every adjustment.', 5, TRUE, 1),
    (2, 'Sample client', 'Founder, D2C brand, Kondapur',
     'Example: GST reconciliations every month mean we no longer lose input credit.', 5, TRUE, 2),
    (3, 'Sample client', 'Finance head, pharma distributor',
     'Example: one point of contact and weekly updates made the transition easy.', 5, TRUE, 3);

-- ---------------------------------------------------------------------
-- Team
-- ---------------------------------------------------------------------
INSERT INTO team_member (id, full_name, designation, qualifications, bio, photo_url, location_id, active, sort_order) VALUES
    (1, 'Example: CA A. Sharma', 'Managing Partner', 'FCA, DISA',
     'Leads the assurance practice and statutory audits for listed and unlisted companies.', NULL, 1, TRUE, 1),
    (2, 'Example: CA P. Rao', 'Partner, Direct Tax', 'FCA, LLB',
     'Handles income tax assessments, appeals and transfer pricing.', NULL, 1, TRUE, 2),
    (3, 'Example: CA N. Menon', 'Partner, GST & Indirect Tax', 'ACA',
     'Works with retail, e-commerce and manufacturing clients on GST compliance and notices.', NULL, 2, TRUE, 3),
    (4, 'Example: CS K. Das', 'Head, Company Law', 'ACS',
     'Runs incorporations, board processes and ROC filings.', NULL, 2, TRUE, 4);

-- ---------------------------------------------------------------------
-- Articles
-- ---------------------------------------------------------------------
INSERT INTO article (id, title, slug, summary, body, category, author, status, published_at) VALUES
    (1, 'Tax audit checklist before 30 September', 'tax-audit-checklist',
     'The documents and reconciliations to have ready before your tax auditor starts on Form 3CD.',
     'The documents and reconciliations to have ready before your tax auditor starts on Form 3CD.',
     'Income Tax', 'Apex & Associates', 'PUBLISHED', '2026-09-10 09:00:00'),
    (2, 'Reconciling input tax credit with GSTR-2B', 'itc-reconciliation-gstr-2b',
     'Why a monthly ITC match matters and a simple process for doing it.',
     'Why a monthly ITC match matters and a simple process for doing it.',
     'GST', 'Apex & Associates', 'PUBLISHED', '2026-08-22 09:00:00'),
    (3, 'First-year compliance for a new private limited company', 'first-year-compliance-private-limited',
     'Board meetings, auditor appointment and filings a new company must complete in year one.',
     'Board meetings, auditor appointment and filings a new company must complete in year one.',
     'Company Law', 'Apex & Associates', 'PUBLISHED', '2026-07-30 09:00:00');

-- ---------------------------------------------------------------------
-- Compliance calendar
-- ---------------------------------------------------------------------
INSERT INTO compliance_deadline (due_date, title, law, applies_to, active) VALUES
    ('2026-09-30', 'Tax audit report (Form 3CA/3CB-3CD) for FY 2025-26', 'INCOME_TAX', 'Businesses and professionals liable to tax audit', TRUE),
    ('2026-10-07', 'TDS / TCS deposit for September', 'TDS', 'All deductors', TRUE),
    ('2026-10-11', 'GSTR-1 for September', 'GST', 'Monthly filers', TRUE),
    ('2026-10-20', 'GSTR-3B for September', 'GST', 'Monthly filers', TRUE),
    ('2026-10-29', 'AOC-4 financial statements for FY 2025-26', 'ROC', 'Companies, within 30 days of the AGM', TRUE),
    ('2026-10-31', 'Income tax return for audit cases, FY 2025-26', 'INCOME_TAX', 'Companies and audited taxpayers', TRUE),
    ('2026-11-07', 'TDS / TCS deposit for October', 'TDS', 'All deductors', TRUE),
    ('2026-11-11', 'GSTR-1 for October', 'GST', 'Monthly filers', TRUE),
    ('2026-11-20', 'GSTR-3B for October', 'GST', 'Monthly filers', TRUE),
    ('2026-11-29', 'MGT-7 annual return for FY 2025-26', 'ROC', 'Companies, within 60 days of the AGM', TRUE),
    ('2026-11-30', 'Income tax return for transfer pricing cases', 'INCOME_TAX', 'Taxpayers filing Form 3CEB', TRUE),
    ('2026-12-31', 'GSTR-9 / 9C annual return for FY 2025-26', 'GST', 'Registered taxpayers above the threshold', TRUE);

-- ---------------------------------------------------------------------
-- Careers
-- ---------------------------------------------------------------------
INSERT INTO job_opening (id, title, slug, location_id, department, experience_range, employment_type, description, active, posted_at, closes_on) VALUES
    (1, 'Audit Associate', 'audit-associate-hyderabad', 1, 'Assurance', '1-3 years', 'FULL_TIME',
     'Example: assist on statutory and tax audits, prepare working papers and coordinate with client finance teams.',
     TRUE, '2026-09-15 09:00:00', '2026-10-31'),
    (2, 'Articled Assistant', 'articled-assistant-hyderabad', 1, 'Assurance & Tax', 'CA Intermediate cleared', 'ARTICLESHIP',
     'Example: three-year articleship covering audit, direct tax and GST.',
     TRUE, '2026-09-15 09:00:00', NULL),
    (3, 'GST Executive', 'gst-executive-bengaluru', 2, 'Indirect Tax', '2-4 years', 'FULL_TIME',
     'Example: prepare GSTR-1, 3B and 9 filings, reconcile ITC with GSTR-2B and draft notice replies.',
     TRUE, '2026-09-20 09:00:00', '2026-11-15');

-- ---------------------------------------------------------------------
-- Sample leads for the back-office dashboard
-- ---------------------------------------------------------------------
INSERT INTO enquiry (id, reference_no, full_name, email, phone, company_name, service_id, location_id, message, source_page, status, created_at, updated_at) VALUES
    (1, 'ENQ-2026-000001', 'Example: S. Iyer', 'siyer@example.com', '+91 90000 00003', NULL, 6, 2,
     'Incorporate a private limited company with two founders.', '/contact', 'PROPOSAL_SENT', '2026-09-22 11:05:00', '2026-09-22 11:05:00'),
    (2, 'ENQ-2026-000002', 'Example: A. Reddy', 'areddy@example.com', '+91 90000 00002', 'Example Labs Pvt Ltd', 1, 1,
     'Statutory audit for FY 2025-26, AGM planned for December.', '/services/statutory-audit', 'CONTACTED', '2026-09-23 16:40:00', '2026-09-23 16:40:00'),
    (3, 'ENQ-2026-000003', 'Example: R. Kumar', 'rkumar@example.com', '+91 90000 00001', 'Kondapur Traders', 4, 1,
     'New GST registration for a second branch.', '/', 'NEW', '2026-09-24 10:12:00', '2026-09-24 10:12:00');
