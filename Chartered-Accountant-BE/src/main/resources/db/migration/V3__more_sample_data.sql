-- =====================================================================
-- Bulk sample data on top of V2, for local testing and demos.
-- Generated; every name, email (example.com), phone and address is a
-- placeholder. Foreign keys are looked up by slug, so no ids are fixed.
-- Back-office users are left out on purpose: the default admin is only
-- created at startup when admin_user is empty.
-- =====================================================================

-- ---------------------------------------------------------------------
-- Offices
-- ---------------------------------------------------------------------

INSERT INTO office_location (city, slug, hero_heading, intro, areas_served, address_line, phone, email, office_hours, head_office, active, sort_order) VALUES
    ('Chennai', 'chennai', 'Audit, tax and compliance for Chennai businesses', 'Our Chennai team supports manufacturers, auto-component makers and IT services firms on audit, GST and direct tax.', 'T. Nagar,Anna Salai,Guindy,OMR,Velachery,Nungambakkam', '3rd Floor, Example Chambers, Anna Salai, Chennai 600002', '+91 44 0000 0000', 'chennai@yourfirm.in', 'Mon-Sat, 9:30 am - 6:30 pm', FALSE, TRUE, 3),
    ('Mumbai', 'mumbai', 'Audit, tax and compliance for Mumbai businesses', 'From BKC to Thane, our Mumbai office works with financial services, media and trading businesses on audit, tax and FEMA compliance.', 'BKC,Andheri East,Lower Parel,Powai,Nariman Point,Thane', '7th Floor, Sample Plaza, Bandra Kurla Complex, Mumbai 400051', '+91 22 0000 0000', 'mumbai@yourfirm.in', 'Mon-Sat, 9:30 am - 6:30 pm', FALSE, TRUE, 4),
    ('Pune', 'pune', 'Audit, tax and compliance for Pune businesses', 'Our Pune team works with auto-component makers, IT companies and startups on statutory audit, GST and payroll.', 'Hinjewadi,Kharadi,Baner,Magarpatta,Shivajinagar', '5th Floor, Demo Business Park, Baner Road, Pune 411045', '+91 20 0000 0000', 'pune@yourfirm.in', 'Mon-Sat, 9:30 am - 6:30 pm', FALSE, TRUE, 5),
    ('Delhi NCR', 'delhi-ncr', 'Audit, tax and compliance for Delhi NCR businesses', 'Our NCR office handles audit, international tax and company law for subsidiaries, exporters and family businesses across Delhi, Gurugram and Noida.', 'Connaught Place,Gurugram,Noida,Nehru Place,Saket', '9th Floor, Example Tower, Cyber City, Gurugram 122002', '+91 124 000 0000', 'delhincr@yourfirm.in', 'Mon-Sat, 9:30 am - 6:30 pm', FALSE, TRUE, 6);


-- ---------------------------------------------------------------------
-- Services and highlights
-- ---------------------------------------------------------------------

INSERT INTO firm_service (category_id, title, slug, summary, body, icon, featured, active, sort_order) VALUES
    ((SELECT id FROM service_category WHERE slug = 'assurance'), 'Tax Audit', 'tax-audit', 'Tax audit under section 44AB with Form 3CD reporting, done well before the due date.', 'Tax audit under section 44AB with Form 3CD reporting, done well before the due date. Our team plans the engagement around your timelines, shares a clear document checklist at the start, and keeps you updated until the work is closed.', 'audit', TRUE, TRUE, 9),
    ((SELECT id FROM service_category WHERE slug = 'assurance'), 'Stock & Concurrent Audit', 'stock-audit', 'Stock audits for banks and concurrent audits of branches, with quick turnaround reports.', 'Stock audits for banks and concurrent audits of branches, with quick turnaround reports. Our team plans the engagement around your timelines, shares a clear document checklist at the start, and keeps you updated until the work is closed.', 'audit', FALSE, TRUE, 10),
    ((SELECT id FROM service_category WHERE slug = 'taxation'), 'GST Audit & Annual Returns', 'gst-audit', 'GSTR-9 and GSTR-9C preparation, reconciliation with books and self-certification.', 'GSTR-9 and GSTR-9C preparation, reconciliation with books and self-certification. Our team plans the engagement around your timelines, shares a clear document checklist at the start, and keeps you updated until the work is closed.', 'gst', FALSE, TRUE, 11),
    ((SELECT id FROM service_category WHERE slug = 'taxation'), 'TDS & TCS Compliance', 'tds-compliance', 'Monthly deposits, quarterly returns, certificates and lower-deduction applications.', 'Monthly deposits, quarterly returns, certificates and lower-deduction applications. Our team plans the engagement around your timelines, shares a clear document checklist at the start, and keeps you updated until the work is closed.', 'tax', TRUE, TRUE, 12),
    ((SELECT id FROM service_category WHERE slug = 'taxation'), 'NRI Taxation', 'nri-taxation', 'Income tax returns, repatriation, Form 15CA/CB and DTAA relief for non-resident Indians.', 'Income tax returns, repatriation, Form 15CA/CB and DTAA relief for non-resident Indians. Our team plans the engagement around your timelines, shares a clear document checklist at the start, and keeps you updated until the work is closed.', 'globe', FALSE, TRUE, 13),
    ((SELECT id FROM service_category WHERE slug = 'company-law'), 'LLP & Firm Registration', 'llp-registration', 'Limited liability partnership and partnership firm registration with drafting of the agreement.', 'Limited liability partnership and partnership firm registration with drafting of the agreement. Our team plans the engagement around your timelines, shares a clear document checklist at the start, and keeps you updated until the work is closed.', 'building', FALSE, TRUE, 14),
    ((SELECT id FROM service_category WHERE slug = 'company-law'), 'ROC & Secretarial Compliance', 'roc-compliance', 'Annual filings, board and general meeting minutes, statutory registers and event-based filings.', 'Annual filings, board and general meeting minutes, statutory registers and event-based filings. Our team plans the engagement around your timelines, shares a clear document checklist at the start, and keeps you updated until the work is closed.', 'document', TRUE, TRUE, 15),
    ((SELECT id FROM service_category WHERE slug = 'company-law'), 'FEMA & RBI Compliance', 'fema-compliance', 'FDI and ODI reporting, FC-GPR, FLA returns and compounding applications.', 'FDI and ODI reporting, FC-GPR, FLA returns and compounding applications. Our team plans the engagement around your timelines, shares a clear document checklist at the start, and keeps you updated until the work is closed.', 'globe', FALSE, TRUE, 16),
    ((SELECT id FROM service_category WHERE slug = 'advisory'), 'Bookkeeping & Accounting', 'bookkeeping', 'Cloud bookkeeping on Tally, Zoho Books or QuickBooks with monthly closing and reconciliations.', 'Cloud bookkeeping on Tally, Zoho Books or QuickBooks with monthly closing and reconciliations. Our team plans the engagement around your timelines, shares a clear document checklist at the start, and keeps you updated until the work is closed.', 'ledger', FALSE, TRUE, 17),
    ((SELECT id FROM service_category WHERE slug = 'advisory'), 'Startup Advisory', 'startup-advisory', 'Incorporation, DPIIT recognition, ESOP plans, fundraising support and due diligence.', 'Incorporation, DPIIT recognition, ESOP plans, fundraising support and due diligence. Our team plans the engagement around your timelines, shares a clear document checklist at the start, and keeps you updated until the work is closed.', 'rocket', TRUE, TRUE, 18),
    ((SELECT id FROM service_category WHERE slug = 'advisory'), 'Business Valuation', 'business-valuation', 'Valuation reports under income tax, FEMA and Companies Act rules for share issues and transfers.', 'Valuation reports under income tax, FEMA and Companies Act rules for share issues and transfers. Our team plans the engagement around your timelines, shares a clear document checklist at the start, and keeps you updated until the work is closed.', 'chart', FALSE, TRUE, 19),
    ((SELECT id FROM service_category WHERE slug = 'advisory'), 'Project Finance & CMA Reports', 'project-finance', 'Project reports and CMA data for term loans and working-capital limits.', 'Project reports and CMA data for term loans and working-capital limits. Our team plans the engagement around your timelines, shares a clear document checklist at the start, and keeps you updated until the work is closed.', 'bank', FALSE, TRUE, 20);

INSERT INTO service_highlight (service_id, text, sort_order) VALUES
    ((SELECT id FROM firm_service WHERE slug = 'tax-audit'), 'Form 3CA / 3CB and 3CD reporting', 1),
    ((SELECT id FROM firm_service WHERE slug = 'tax-audit'), 'Reconciliation of books with GST and TDS returns', 2),
    ((SELECT id FROM firm_service WHERE slug = 'tax-audit'), 'Clause-by-clause review with management', 3),
    ((SELECT id FROM firm_service WHERE slug = 'tax-audit'), 'Filing on the income tax portal', 4),
    ((SELECT id FROM firm_service WHERE slug = 'stock-audit'), 'Bank stock and receivables audits', 1),
    ((SELECT id FROM firm_service WHERE slug = 'stock-audit'), 'Concurrent audit of bank branches', 2),
    ((SELECT id FROM firm_service WHERE slug = 'stock-audit'), 'Drawing power and margin checks', 3),
    ((SELECT id FROM firm_service WHERE slug = 'gst-audit'), 'GSTR-9 annual return', 1),
    ((SELECT id FROM firm_service WHERE slug = 'gst-audit'), 'GSTR-9C reconciliation statement', 2),
    ((SELECT id FROM firm_service WHERE slug = 'gst-audit'), 'Turnover and ITC reconciliation with books', 3),
    ((SELECT id FROM firm_service WHERE slug = 'gst-audit'), 'Identification of tax exposure before filing', 4),
    ((SELECT id FROM firm_service WHERE slug = 'tds-compliance'), 'Monthly TDS / TCS computation and deposit', 1),
    ((SELECT id FROM firm_service WHERE slug = 'tds-compliance'), 'Quarterly returns 24Q, 26Q and 27Q', 2),
    ((SELECT id FROM firm_service WHERE slug = 'tds-compliance'), 'Form 16 / 16A generation', 3),
    ((SELECT id FROM firm_service WHERE slug = 'tds-compliance'), 'Default notice resolution on TRACES', 4),
    ((SELECT id FROM firm_service WHERE slug = 'nri-taxation'), 'Residential status review', 1),
    ((SELECT id FROM firm_service WHERE slug = 'nri-taxation'), 'Returns for rental and capital gains income', 2),
    ((SELECT id FROM firm_service WHERE slug = 'nri-taxation'), 'Form 15CA / 15CB certification', 3),
    ((SELECT id FROM firm_service WHERE slug = 'nri-taxation'), 'DTAA relief and lower-deduction certificates', 4),
    ((SELECT id FROM firm_service WHERE slug = 'llp-registration'), 'LLP incorporation and LLP agreement', 1),
    ((SELECT id FROM firm_service WHERE slug = 'llp-registration'), 'Partnership deed drafting and registration', 2),
    ((SELECT id FROM firm_service WHERE slug = 'llp-registration'), 'Conversion of firm to LLP', 3),
    ((SELECT id FROM firm_service WHERE slug = 'roc-compliance'), 'AOC-4 and MGT-7 annual filings', 1),
    ((SELECT id FROM firm_service WHERE slug = 'roc-compliance'), 'Board and AGM minutes and resolutions', 2),
    ((SELECT id FROM firm_service WHERE slug = 'roc-compliance'), 'Director KYC and change filings', 3),
    ((SELECT id FROM firm_service WHERE slug = 'roc-compliance'), 'Statutory registers maintenance', 4),
    ((SELECT id FROM firm_service WHERE slug = 'fema-compliance'), 'FC-GPR and FC-TRS filings', 1),
    ((SELECT id FROM firm_service WHERE slug = 'fema-compliance'), 'Annual FLA return', 2),
    ((SELECT id FROM firm_service WHERE slug = 'fema-compliance'), 'ODI reporting and approvals', 3),
    ((SELECT id FROM firm_service WHERE slug = 'fema-compliance'), 'Compounding of contraventions', 4),
    ((SELECT id FROM firm_service WHERE slug = 'bookkeeping'), 'Daily or weekly bookkeeping', 1),
    ((SELECT id FROM firm_service WHERE slug = 'bookkeeping'), 'Bank and vendor reconciliations', 2),
    ((SELECT id FROM firm_service WHERE slug = 'bookkeeping'), 'Monthly closing and trial balance', 3),
    ((SELECT id FROM firm_service WHERE slug = 'bookkeeping'), 'Accounts payable and receivable tracking', 4),
    ((SELECT id FROM firm_service WHERE slug = 'startup-advisory'), 'DPIIT recognition and 80-IAC exemption', 1),
    ((SELECT id FROM firm_service WHERE slug = 'startup-advisory'), 'ESOP scheme design and valuation', 2),
    ((SELECT id FROM firm_service WHERE slug = 'startup-advisory'), 'Financial model and data room preparation', 3),
    ((SELECT id FROM firm_service WHERE slug = 'startup-advisory'), 'Investor due diligence support', 4),
    ((SELECT id FROM firm_service WHERE slug = 'business-valuation'), 'Rule 11UA valuations', 1),
    ((SELECT id FROM firm_service WHERE slug = 'business-valuation'), 'FEMA pricing guideline valuations', 2),
    ((SELECT id FROM firm_service WHERE slug = 'business-valuation'), 'Registered valuer reports for the Companies Act', 3),
    ((SELECT id FROM firm_service WHERE slug = 'business-valuation'), 'Brand and intangible asset valuation', 4),
    ((SELECT id FROM firm_service WHERE slug = 'project-finance'), 'Detailed project reports', 1),
    ((SELECT id FROM firm_service WHERE slug = 'project-finance'), 'CMA data for bank limits', 2),
    ((SELECT id FROM firm_service WHERE slug = 'project-finance'), 'Loan syndication support', 3);


-- ---------------------------------------------------------------------
-- Industries
-- ---------------------------------------------------------------------

INSERT INTO industry (name, slug, summary, icon, sort_order) VALUES
    ('Manufacturing', 'manufacturing', 'Engineering, textiles and FMCG plants', 'factory', 9),
    ('Healthcare', 'healthcare', 'Hospitals, clinics and diagnostics', 'heart', 10),
    ('Education', 'education', 'Schools, colleges and edtech', 'book', 11),
    ('NGOs & Trusts', 'ngos-trusts', 'Section 8 companies, trusts and societies', 'hands', 12),
    ('Logistics', 'logistics', 'Transporters, warehousing and 3PL', 'truck', 13),
    ('Financial Services', 'financial-services', 'NBFCs, brokers and fintechs', 'bank', 14),
    ('Agriculture & Food', 'agriculture-food', 'Agri-processing, dairy and exporters', 'leaf', 15);


-- ---------------------------------------------------------------------
-- FAQs
-- ---------------------------------------------------------------------

INSERT INTO faq (question, answer, location_id, service_id, active, sort_order) VALUES
    ('Which areas of Chennai do you serve?', 'Our Chennai office serves clients in T. Nagar, Anna Salai, Guindy, OMR, Velachery and Nungambakkam. Most work is done online, so we also support clients elsewhere in the region.', (SELECT id FROM office_location WHERE slug = 'chennai'), NULL, TRUE, 9),
    ('Can I visit your Chennai office without an appointment?', 'We recommend booking a slot through the enquiry form or by phone so the right person is available, but walk-ins are welcome during office hours.', (SELECT id FROM office_location WHERE slug = 'chennai'), NULL, TRUE, 10),
    ('Which areas of Mumbai do you serve?', 'Our Mumbai office serves clients in BKC, Andheri East, Lower Parel, Powai, Nariman Point and Thane. Most work is done online, so we also support clients elsewhere in the region.', (SELECT id FROM office_location WHERE slug = 'mumbai'), NULL, TRUE, 11),
    ('Can I visit your Mumbai office without an appointment?', 'We recommend booking a slot through the enquiry form or by phone so the right person is available, but walk-ins are welcome during office hours.', (SELECT id FROM office_location WHERE slug = 'mumbai'), NULL, TRUE, 12),
    ('Which areas of Pune do you serve?', 'Our Pune office serves clients in Hinjewadi, Kharadi, Baner, Magarpatta and Shivajinagar. Most work is done online, so we also support clients elsewhere in the region.', (SELECT id FROM office_location WHERE slug = 'pune'), NULL, TRUE, 13),
    ('Can I visit your Pune office without an appointment?', 'We recommend booking a slot through the enquiry form or by phone so the right person is available, but walk-ins are welcome during office hours.', (SELECT id FROM office_location WHERE slug = 'pune'), NULL, TRUE, 14),
    ('Which areas of Delhi NCR do you serve?', 'Our Delhi NCR office serves clients in Connaught Place, Gurugram, Noida, Nehru Place and Saket. Most work is done online, so we also support clients elsewhere in the region.', (SELECT id FROM office_location WHERE slug = 'delhi-ncr'), NULL, TRUE, 15),
    ('Can I visit your Delhi NCR office without an appointment?', 'We recommend booking a slot through the enquiry form or by phone so the right person is available, but walk-ins are welcome during office hours.', (SELECT id FROM office_location WHERE slug = 'delhi-ncr'), NULL, TRUE, 16),
    ('What are your fees?', 'Fees depend on the scope, the size of the business and the number of filings. We share a written fee proposal after a free first call.', NULL, NULL, TRUE, 17),
    ('Do you work with clients outside your office cities?', 'Yes. Most of our work is done online through secure document sharing and video calls, so we work with clients across India and abroad.', NULL, NULL, TRUE, 18),
    ('How do you keep my documents secure?', 'Documents are shared through an access-controlled portal, and our team works under confidentiality agreements. We never share client data with third parties.', NULL, NULL, TRUE, 19),
    ('Which accounting software do you support?', 'We work with Tally, Zoho Books, QuickBooks and SAP Business One, and can also take over bookkeeping on your existing system.', NULL, NULL, TRUE, 20),
    ('Can you represent me before the tax department?', 'Yes. Our chartered accountants represent clients in assessments, appeals before the CIT(A) and GST proceedings.', NULL, NULL, TRUE, 21),
    ('How quickly can you start?', 'For most engagements we can start within a week of receiving the signed engagement letter and the first set of documents.', NULL, NULL, TRUE, 22),
    ('Who needs a tax audit?', 'Businesses with turnover above the section 44AB limit, and professionals with gross receipts above the prescribed limit, need a tax audit. Lower limits apply in some presumptive-tax cases.', NULL, (SELECT id FROM firm_service WHERE slug = 'tax-audit'), TRUE, 23),
    ('What is the due date for the tax audit report?', 'The tax audit report is due by 30 September of the assessment year, one month before the return due date for audit cases.', NULL, (SELECT id FROM firm_service WHERE slug = 'tax-audit'), TRUE, 24),
    ('What happens if TDS is deposited late?', 'Late deposit attracts interest at 1.5% per month, and late filing of returns attracts a fee under section 234E. We track due dates so this does not happen.', NULL, (SELECT id FROM firm_service WHERE slug = 'tds-compliance'), TRUE, 25),
    ('Do NRIs need to file an income tax return in India?', 'An NRI must file a return if Indian income exceeds the basic exemption limit or if they want to claim a refund of excess TDS.', NULL, (SELECT id FROM firm_service WHERE slug = 'nri-taxation'), TRUE, 26),
    ('What happens if annual ROC filings are missed?', 'Late filings attract additional fees for each day of delay, and continued default can lead to disqualification of directors.', NULL, (SELECT id FROM firm_service WHERE slug = 'roc-compliance'), TRUE, 27),
    ('What are the benefits of DPIIT recognition?', 'Recognised startups can apply for the section 80-IAC tax holiday, angel-tax relief and self-certification under certain labour and environment laws.', NULL, (SELECT id FROM firm_service WHERE slug = 'startup-advisory'), TRUE, 28),
    ('Can you do bookkeeping remotely?', 'Yes. We work on cloud software or through remote access to your system and share monthly reports.', NULL, (SELECT id FROM firm_service WHERE slug = 'bookkeeping'), TRUE, 29),
    ('Is GSTR-9C still required?', 'GSTR-9C is self-certified and applies to taxpayers above the turnover threshold notified for the year.', NULL, (SELECT id FROM firm_service WHERE slug = 'gst-audit'), TRUE, 30);


-- ---------------------------------------------------------------------
-- Testimonials
-- ---------------------------------------------------------------------

INSERT INTO testimonial (author_name, author_title, quote, rating, active, sort_order) VALUES
    ('Sample client', 'Managing Director, auto-component maker, Pune', 'Example: they sorted out three years of pending GST reconciliations in two months.', 4, TRUE, 4),
    ('Sample client', 'CFO, SaaS company, Bengaluru', 'Example: the virtual CFO team gave us board-ready MIS from the first month.', 5, TRUE, 5),
    ('Sample client', 'Founder, D2C brand, Mumbai', 'Example: incorporation, GST and our first funding round were handled without a single missed deadline.', 5, TRUE, 6),
    ('Sample client', 'Director, pharma distributor, Hyderabad', 'Example: clear communication and no surprises at audit time.', 4, TRUE, 7),
    ('Sample client', 'Partner, architecture firm, Chennai', 'Example: they explained our tax position in plain language and saved us a notice.', 5, TRUE, 8),
    ('Sample client', 'Country head, Indian subsidiary, Gurugram', 'Example: transfer pricing documentation was ready weeks before the due date.', 5, TRUE, 9),
    ('Sample client', 'Trustee, education trust, Chennai', 'Example: 12A and 80G registrations came through smoothly.', 4, TRUE, 10),
    ('Sample client', 'Owner, restaurant chain, Hyderabad', 'Example: payroll and GST for six outlets now run on autopilot.', 5, TRUE, 11),
    ('Sample client', 'NRI client, Dubai', 'Example: repatriating sale proceeds of my flat was far simpler than I expected.', 5, TRUE, 12);


-- ---------------------------------------------------------------------
-- Team
-- ---------------------------------------------------------------------

INSERT INTO team_member (full_name, designation, qualifications, bio, photo_url, location_id, active, sort_order) VALUES
    ('Example: CA R. Iyer', 'Partner, Assurance', 'FCA', 'Works in our Chennai office on assurance engagements for mid-sized companies and startups.', NULL, (SELECT id FROM office_location WHERE slug = 'chennai'), TRUE, 5),
    ('Example: CA M. Joshi', 'Partner, Direct Tax', 'FCA, CS', 'Works in our Mumbai office on direct tax engagements for mid-sized companies and startups.', NULL, (SELECT id FROM office_location WHERE slug = 'mumbai'), TRUE, 6),
    ('Example: CA S. Kulkarni', 'Partner, Audit', 'FCA, DISA', 'Works in our Pune office on audit engagements for mid-sized companies and startups.', NULL, (SELECT id FROM office_location WHERE slug = 'pune'), TRUE, 7),
    ('Example: CA V. Malhotra', 'Partner, International Tax', 'FCA, LLM', 'Works in our Delhi NCR office on international tax engagements for mid-sized companies and startups.', NULL, (SELECT id FROM office_location WHERE slug = 'delhi-ncr'), TRUE, 8),
    ('Example: CA D. Pillai', 'Senior Manager, Audit', 'ACA', 'Works in our Chennai office on audit engagements for mid-sized companies and startups.', NULL, (SELECT id FROM office_location WHERE slug = 'chennai'), TRUE, 9),
    ('Example: CA H. Shah', 'Senior Manager, GST', 'ACA', 'Works in our Mumbai office on gst engagements for mid-sized companies and startups.', NULL, (SELECT id FROM office_location WHERE slug = 'mumbai'), TRUE, 10),
    ('Example: CA T. Deshmukh', 'Manager, Virtual CFO', 'ACA, CFA L2', 'Works in our Pune office on virtual cfo engagements for mid-sized companies and startups.', NULL, (SELECT id FROM office_location WHERE slug = 'pune'), TRUE, 11),
    ('Example: CA A. Kapoor', 'Manager, Transfer Pricing', 'ACA', 'Works in our Delhi NCR office on transfer pricing engagements for mid-sized companies and startups.', NULL, (SELECT id FROM office_location WHERE slug = 'delhi-ncr'), TRUE, 12),
    ('Example: CA L. Reddy', 'Manager, Direct Tax', 'ACA', 'Works in our Hyderabad office on direct tax engagements for mid-sized companies and startups.', NULL, (SELECT id FROM office_location WHERE slug = 'hyderabad'), TRUE, 13),
    ('Example: CA B. Gowda', 'Manager, Startup Advisory', 'ACA', 'Works in our Bengaluru office on startup advisory engagements for mid-sized companies and startups.', NULL, (SELECT id FROM office_location WHERE slug = 'bengaluru'), TRUE, 14),
    ('Example: CS P. Nair', 'Company Secretary', 'ACS, LLB', 'Works in our Chennai office on company secretary engagements for mid-sized companies and startups.', NULL, (SELECT id FROM office_location WHERE slug = 'chennai'), TRUE, 15),
    ('Example: CS R. Mehta', 'Company Secretary', 'ACS', 'Works in our Mumbai office on company secretary engagements for mid-sized companies and startups.', NULL, (SELECT id FROM office_location WHERE slug = 'mumbai'), TRUE, 16),
    ('Example: CA K. Varma', 'Assistant Manager, Payroll', 'ACA', 'Works in our Hyderabad office on payroll engagements for mid-sized companies and startups.', NULL, (SELECT id FROM office_location WHERE slug = 'hyderabad'), TRUE, 17),
    ('Example: CA J. Thomas', 'Assistant Manager, Audit', 'ACA', 'Works in our Bengaluru office on audit engagements for mid-sized companies and startups.', NULL, (SELECT id FROM office_location WHERE slug = 'bengaluru'), TRUE, 18),
    ('Example: S. Banerjee', 'Senior Executive, Bookkeeping', 'M.Com', 'Works in our Delhi NCR office on bookkeeping engagements for mid-sized companies and startups.', NULL, (SELECT id FROM office_location WHERE slug = 'delhi-ncr'), TRUE, 19),
    ('Example: N. Rao', 'Senior Executive, GST', 'B.Com, CA Inter', 'Works in our Hyderabad office on gst engagements for mid-sized companies and startups.', NULL, (SELECT id FROM office_location WHERE slug = 'hyderabad'), TRUE, 20);


-- ---------------------------------------------------------------------
-- Articles
-- ---------------------------------------------------------------------

INSERT INTO article (title, slug, summary, body, category, author, status, published_at) VALUES
    ('How to respond to a GST notice', 'how-to-respond-to-a-gst-notice', 'Common GST notices, what they mean, and how to reply within the time limit.', 'Common GST notices, what they mean, and how to reply within the time limit. In this article we walk through the rules that apply, the documents you should keep ready, and the practical steps our team follows with clients. The figures and thresholds used here are illustrative; check the current law or speak to us before acting on them.', 'GST', 'Apex & Associates', 'PUBLISHED', '2026-07-05 09:00:00'),
    ('New tax regime vs old regime: which suits you?', 'new-tax-regime-vs-old-regime-which-suits-you', 'A worked comparison for salaried employees and business owners.', 'A worked comparison for salaried employees and business owners. In this article we walk through the rules that apply, the documents you should keep ready, and the practical steps our team follows with clients. The figures and thresholds used here are illustrative; check the current law or speak to us before acting on them.', 'Income Tax', 'Apex & Associates', 'PUBLISHED', '2026-07-10 09:00:00'),
    ('Advance tax instalments explained', 'advance-tax-instalments-explained', 'Who pays advance tax, the four instalment dates and the interest for shortfalls.', 'Who pays advance tax, the four instalment dates and the interest for shortfalls. In this article we walk through the rules that apply, the documents you should keep ready, and the practical steps our team follows with clients. The figures and thresholds used here are illustrative; check the current law or speak to us before acting on them.', 'Income Tax', 'Apex & Associates', 'PUBLISHED', '2026-07-15 09:00:00'),
    ('E-invoicing under GST: thresholds and process', 'e-invoicing-under-gst-thresholds-and-process', 'Who must generate e-invoices, how the IRN works and common mistakes.', 'Who must generate e-invoices, how the IRN works and common mistakes. In this article we walk through the rules that apply, the documents you should keep ready, and the practical steps our team follows with clients. The figures and thresholds used here are illustrative; check the current law or speak to us before acting on them.', 'GST', 'Apex & Associates', 'PUBLISHED', '2026-07-20 09:00:00'),
    ('Director KYC: the annual DIR-3 KYC filing', 'director-kyc-the-annual-dir-3-kyc-filing', 'Why every director must file DIR-3 KYC and what happens if it is missed.', 'Why every director must file DIR-3 KYC and what happens if it is missed. In this article we walk through the rules that apply, the documents you should keep ready, and the practical steps our team follows with clients. The figures and thresholds used here are illustrative; check the current law or speak to us before acting on them.', 'Company Law', 'Apex & Associates', 'PUBLISHED', '2026-07-25 09:00:00'),
    ('Setting up an Indian subsidiary: a checklist', 'setting-up-an-indian-subsidiary-a-checklist', 'Approvals, filings and bank steps for foreign companies entering India.', 'Approvals, filings and bank steps for foreign companies entering India. In this article we walk through the rules that apply, the documents you should keep ready, and the practical steps our team follows with clients. The figures and thresholds used here are illustrative; check the current law or speak to us before acting on them.', 'Company Law', 'Apex & Associates', 'PUBLISHED', '2026-07-30 09:00:00'),
    ('TDS on rent, contractors and professionals', 'tds-on-rent-contractors-and-professionals', 'Rates, thresholds and due dates for the most common TDS sections.', 'Rates, thresholds and due dates for the most common TDS sections. In this article we walk through the rules that apply, the documents you should keep ready, and the practical steps our team follows with clients. The figures and thresholds used here are illustrative; check the current law or speak to us before acting on them.', 'TDS', 'Apex & Associates', 'PUBLISHED', '2026-08-04 09:00:00'),
    ('Capital gains on sale of property', 'capital-gains-on-sale-of-property', 'Holding periods, indexation changes and exemptions under sections 54 and 54F.', 'Holding periods, indexation changes and exemptions under sections 54 and 54F. In this article we walk through the rules that apply, the documents you should keep ready, and the practical steps our team follows with clients. The figures and thresholds used here are illustrative; check the current law or speak to us before acting on them.', 'Income Tax', 'Apex & Associates', 'PUBLISHED', '2026-08-09 09:00:00'),
    ('Monthly closing checklist for finance teams', 'monthly-closing-checklist-for-finance-teams', 'A simple 10-step month-end close that keeps MIS and compliance on track.', 'A simple 10-step month-end close that keeps MIS and compliance on track. In this article we walk through the rules that apply, the documents you should keep ready, and the practical steps our team follows with clients. The figures and thresholds used here are illustrative; check the current law or speak to us before acting on them.', 'Advisory', 'Apex & Associates', 'PUBLISHED', '2026-08-14 09:00:00'),
    ('ESOPs for startups: tax and accounting', 'esops-for-startups-tax-and-accounting', 'How ESOPs are taxed at exercise and sale, and how to account for them.', 'How ESOPs are taxed at exercise and sale, and how to account for them. In this article we walk through the rules that apply, the documents you should keep ready, and the practical steps our team follows with clients. The figures and thresholds used here are illustrative; check the current law or speak to us before acting on them.', 'Advisory', 'Apex & Associates', 'PUBLISHED', '2026-08-19 09:00:00'),
    ('Form 15CA and 15CB: when are they required?', 'form-15ca-and-15cb-when-are-they-required', 'Reporting requirements for payments to non-residents.', 'Reporting requirements for payments to non-residents. In this article we walk through the rules that apply, the documents you should keep ready, and the practical steps our team follows with clients. The figures and thresholds used here are illustrative; check the current law or speak to us before acting on them.', 'International Tax', 'Apex & Associates', 'PUBLISHED', '2026-08-24 09:00:00'),
    ('Transfer pricing: documentation essentials', 'transfer-pricing-documentation-essentials', 'What a TP study must contain and how to benchmark related-party transactions.', 'What a TP study must contain and how to benchmark related-party transactions. In this article we walk through the rules that apply, the documents you should keep ready, and the practical steps our team follows with clients. The figures and thresholds used here are illustrative; check the current law or speak to us before acting on them.', 'International Tax', 'Apex & Associates', 'PUBLISHED', '2026-08-29 09:00:00'),
    ('Composition scheme under GST', 'composition-scheme-under-gst', 'Eligibility, tax rates and the trade-offs of opting in.', 'Eligibility, tax rates and the trade-offs of opting in. In this article we walk through the rules that apply, the documents you should keep ready, and the practical steps our team follows with clients. The figures and thresholds used here are illustrative; check the current law or speak to us before acting on them.', 'GST', 'Apex & Associates', 'PUBLISHED', '2026-09-03 09:00:00'),
    ('Presumptive taxation for small businesses', 'presumptive-taxation-for-small-businesses', 'Sections 44AD, 44ADA and 44AE explained with examples.', 'Sections 44AD, 44ADA and 44AE explained with examples. In this article we walk through the rules that apply, the documents you should keep ready, and the practical steps our team follows with clients. The figures and thresholds used here are illustrative; check the current law or speak to us before acting on them.', 'Income Tax', 'Apex & Associates', 'PUBLISHED', '2026-09-08 09:00:00'),
    ('Registering a trust for 12A and 80G', 'registering-a-trust-for-12a-and-80g', 'Steps and documents for tax exemption and donor deductions.', 'Steps and documents for tax exemption and donor deductions. In this article we walk through the rules that apply, the documents you should keep ready, and the practical steps our team follows with clients. The figures and thresholds used here are illustrative; check the current law or speak to us before acting on them.', 'Advisory', 'Apex & Associates', 'DRAFT', NULL),
    ('Common audit observations and how to avoid them', 'common-audit-observations-and-how-to-avoid-them', 'The issues auditors flag most often in mid-sized companies.', 'The issues auditors flag most often in mid-sized companies. In this article we walk through the rules that apply, the documents you should keep ready, and the practical steps our team follows with clients. The figures and thresholds used here are illustrative; check the current law or speak to us before acting on them.', 'Audit', 'Apex & Associates', 'DRAFT', NULL),
    ('MSME payments and section 43B(h)', 'msme-payments-and-section-43b-h', 'Why late payments to micro and small suppliers can cost you a deduction.', 'Why late payments to micro and small suppliers can cost you a deduction. In this article we walk through the rules that apply, the documents you should keep ready, and the practical steps our team follows with clients. The figures and thresholds used here are illustrative; check the current law or speak to us before acting on them.', 'Income Tax', 'Apex & Associates', 'DRAFT', NULL);


-- ---------------------------------------------------------------------
-- Compliance calendar, Jan-Mar 2027
-- ---------------------------------------------------------------------

INSERT INTO compliance_deadline (due_date, title, law, applies_to, active) VALUES
    ('2026-12-15', 'Third instalment of advance tax', 'INCOME_TAX', 'All taxpayers liable to advance tax', TRUE),
    ('2027-01-07', 'TDS / TCS deposit for December', 'TDS', 'All deductors', TRUE),
    ('2027-01-11', 'GSTR-1 for December', 'GST', 'Monthly filers', TRUE),
    ('2027-01-13', 'GSTR-1 (IFF) for October-December quarter', 'GST', 'QRMP filers', TRUE),
    ('2027-01-20', 'GSTR-3B for December', 'GST', 'Monthly filers', TRUE),
    ('2027-01-31', 'TDS return for October-December quarter', 'TDS', 'All deductors', TRUE),
    ('2027-02-07', 'TDS / TCS deposit for January', 'TDS', 'All deductors', TRUE),
    ('2027-02-11', 'GSTR-1 for January', 'GST', 'Monthly filers', TRUE),
    ('2027-02-15', 'Form 16A for October-December quarter', 'TDS', 'Deductors, non-salary payments', TRUE),
    ('2027-02-20', 'GSTR-3B for January', 'GST', 'Monthly filers', TRUE),
    ('2027-03-07', 'TDS / TCS deposit for February', 'TDS', 'All deductors', TRUE),
    ('2027-03-11', 'GSTR-1 for February', 'GST', 'Monthly filers', TRUE),
    ('2027-03-15', 'Fourth instalment of advance tax', 'INCOME_TAX', 'All taxpayers liable to advance tax', TRUE),
    ('2027-03-20', 'GSTR-3B for February', 'GST', 'Monthly filers', TRUE),
    ('2027-03-31', 'Director KYC status review', 'ROC', 'Companies with new directors', TRUE),
    ('2027-03-31', 'Updated return (ITR-U) for FY 2022-23', 'INCOME_TAX', 'Taxpayers correcting earlier returns', TRUE);


-- ---------------------------------------------------------------------
-- Job openings
-- ---------------------------------------------------------------------

INSERT INTO job_opening (title, slug, location_id, department, experience_range, employment_type, description, active, posted_at, closes_on) VALUES
    ('Senior Audit Associate', 'senior-audit-associate-chennai', (SELECT id FROM office_location WHERE slug = 'chennai'), 'Assurance', '3-5 years', 'FULL_TIME', 'Example: join our assurance team to work on engagements for mid-sized companies and startups. You will prepare working papers, coordinate with client teams and grow into client-facing work.', TRUE, '2026-09-01 09:00:00', '2026-11-30'),
    ('Tax Manager', 'tax-manager-mumbai', (SELECT id FROM office_location WHERE slug = 'mumbai'), 'Direct Tax', '6-9 years', 'FULL_TIME', 'Example: join our direct tax team to work on engagements for mid-sized companies and startups. You will prepare working papers, coordinate with client teams and grow into client-facing work.', TRUE, '2026-09-03 09:00:00', '2026-11-30'),
    ('Articled Assistant', 'articled-assistant-pune', (SELECT id FROM office_location WHERE slug = 'pune'), 'Assurance & Tax', 'CA Intermediate cleared', 'ARTICLESHIP', 'Example: join our assurance & tax team to work on engagements for mid-sized companies and startups. You will prepare working papers, coordinate with client teams and grow into client-facing work.', TRUE, '2026-09-05 09:00:00', NULL),
    ('Transfer Pricing Associate', 'transfer-pricing-associate-delhi-ncr', (SELECT id FROM office_location WHERE slug = 'delhi-ncr'), 'International Tax', '2-4 years', 'FULL_TIME', 'Example: join our international tax team to work on engagements for mid-sized companies and startups. You will prepare working papers, coordinate with client teams and grow into client-facing work.', TRUE, '2026-09-07 09:00:00', '2026-11-30'),
    ('Accounts Executive', 'accounts-executive-bengaluru', (SELECT id FROM office_location WHERE slug = 'bengaluru'), 'Outsourcing', '1-3 years', 'FULL_TIME', 'Example: join our outsourcing team to work on engagements for mid-sized companies and startups. You will prepare working papers, coordinate with client teams and grow into client-facing work.', TRUE, '2026-09-09 09:00:00', '2026-11-30'),
    ('Company Secretary Trainee', 'company-secretary-trainee-mumbai', (SELECT id FROM office_location WHERE slug = 'mumbai'), 'Company Law', 'CS Executive cleared', 'INTERNSHIP', 'Example: join our company law team to work on engagements for mid-sized companies and startups. You will prepare working papers, coordinate with client teams and grow into client-facing work.', TRUE, '2026-09-11 09:00:00', '2026-11-30'),
    ('Payroll Executive', 'payroll-executive-hyderabad', (SELECT id FROM office_location WHERE slug = 'hyderabad'), 'Outsourcing', '1-2 years', 'FULL_TIME', 'Example: join our outsourcing team to work on engagements for mid-sized companies and startups. You will prepare working papers, coordinate with client teams and grow into client-facing work.', TRUE, '2026-09-13 09:00:00', '2026-11-30'),
    ('Summer Intern, Audit', 'summer-intern-audit-chennai', (SELECT id FROM office_location WHERE slug = 'chennai'), 'Assurance', 'B.Com students', 'INTERNSHIP', 'Example: join our assurance team to work on engagements for mid-sized companies and startups. You will prepare working papers, coordinate with client teams and grow into client-facing work.', TRUE, '2026-09-15 09:00:00', '2026-11-30'),
    ('Virtual CFO Manager', 'virtual-cfo-manager-bengaluru', (SELECT id FROM office_location WHERE slug = 'bengaluru'), 'Advisory', '5-8 years', 'FULL_TIME', 'Example: join our advisory team to work on engagements for mid-sized companies and startups. You will prepare working papers, coordinate with client teams and grow into client-facing work.', TRUE, '2026-09-17 09:00:00', '2026-11-30');


-- ---------------------------------------------------------------------
-- Sample enquiries (reference numbers are set after the insert)
-- ---------------------------------------------------------------------

INSERT INTO enquiry (full_name, email, phone, company_name, service_id, location_id, message, source_page, status, internal_notes, created_at, updated_at) VALUES
    ('Aarav Sharma', 'aarav.sharma0@example.com', '+91 9786579303', NULL, (SELECT id FROM firm_service WHERE slug = 'gst-registration'), (SELECT id FROM office_location WHERE slug = 'hyderabad'), 'Looking for GST registration and monthly return filing for a new business.', '/', 'CONTACTED', 'Proposal shared by email.', '2026-07-01 12:14:00', '2026-07-04 10:14:00'),
    ('Priya Menon', 'priya.menon1@example.com', '+91 9193349856', 'Placeholder Pharma', (SELECT id FROM firm_service WHERE slug = 'roc-compliance'), (SELECT id FROM office_location WHERE slug = 'mumbai'), 'Annual ROC filings for the last two years are pending.', '/contact', 'NEW', NULL, '2026-07-02 18:41:00', '2026-07-05 11:41:00'),
    ('Rahul Kulkarni', 'rahul.kulkarni2@example.com', '+91 9746412689', 'Demo Constructions', (SELECT id FROM firm_service WHERE slug = 'statutory-audit'), (SELECT id FROM office_location WHERE slug = 'pune'), 'Need a statutory audit for FY 2025-26. AGM is planned for December.', '/services/statutory-audit', 'CONTACTED', 'Proposal shared by email.', '2026-07-04 12:38:00', '2026-07-04 13:38:00'),
    ('Sneha Patel', 'sneha.patel3@example.com', '+91 9914763202', 'Placeholder Pharma', (SELECT id FROM firm_service WHERE slug = 'business-valuation'), (SELECT id FROM office_location WHERE slug = 'bengaluru'), 'Need a valuation report for a share issue to investors.', '/services/business-valuation', 'CONVERTED', 'Proposal shared by email.', '2026-07-05 19:05:00', '2026-07-06 09:05:00'),
    ('Vikram Singh', 'vikram.singh4@example.com', '+91 9199585092', NULL, (SELECT id FROM firm_service WHERE slug = 'tax-audit'), (SELECT id FROM office_location WHERE slug = 'hyderabad'), 'Our turnover crossed the tax audit limit this year. Need the audit done before September.', '/', 'PROPOSAL_SENT', 'Client comparing quotes.', '2026-07-07 05:02:00', '2026-07-09 16:02:00'),
    ('Ananya Bose', 'ananya.bose5@example.com', '+91 9675770529', NULL, (SELECT id FROM firm_service WHERE slug = 'gst-registration'), (SELECT id FROM office_location WHERE slug = 'mumbai'), 'Looking for GST registration and monthly return filing for a new business.', '/services/gst-registration', 'NEW', NULL, '2026-07-08 17:18:00', '2026-07-09 18:18:00'),
    ('Karthik Rao', 'karthik.rao6@example.com', '+91 9856528252', NULL, (SELECT id FROM firm_service WHERE slug = 'gst-registration'), (SELECT id FROM office_location WHERE slug = 'hyderabad'), 'Looking for GST registration and monthly return filing for a new business.', '/', 'CONTACTED', 'Proposal shared by email.', '2026-07-09 22:41:00', '2026-07-11 23:41:00'),
    ('Divya Das', 'divya.das7@example.com', '+91 9398471886', 'Placeholder Pharma', (SELECT id FROM firm_service WHERE slug = 'tds-compliance'), (SELECT id FROM office_location WHERE slug = 'delhi-ncr'), 'Received TDS default notices on TRACES and need them resolved.', '/services/tds-compliance', 'PROPOSAL_SENT', 'Client comparing quotes.', '2026-07-11 06:35:00', '2026-07-11 16:35:00'),
    ('Arjun Iyer', 'arjun.iyer8@example.com', '+91 9754049436', 'Example Motors', (SELECT id FROM firm_service WHERE slug = 'bookkeeping'), (SELECT id FROM office_location WHERE slug = 'bengaluru'), 'Need monthly bookkeeping on Zoho Books.', '/contact', 'CONTACTED', 'Meeting scheduled at the office.', '2026-07-12 16:17:00', '2026-07-14 10:17:00'),
    ('Meera Kumar', 'meera.kumar9@example.com', '+91 9924970419', 'Sample Logistics', (SELECT id FROM firm_service WHERE slug = 'business-valuation'), (SELECT id FROM office_location WHERE slug = 'hyderabad'), 'Need a valuation report for a share issue to investors.', '/services/business-valuation', 'CONTACTED', 'Client comparing quotes.', '2026-07-14 00:15:00', '2026-07-14 09:15:00'),
    ('Rohan Naidu', 'rohan.naidu10@example.com', '+91 9326541099', 'Sample Logistics', (SELECT id FROM firm_service WHERE slug = 'roc-compliance'), (SELECT id FROM office_location WHERE slug = 'delhi-ncr'), 'Annual ROC filings for the last two years are pending.', '/locations/delhi-ncr', 'PROPOSAL_SENT', 'Meeting scheduled at the office.', '2026-07-15 12:41:00', '2026-07-16 07:41:00'),
    ('Kavya Gupta', 'kavya.gupta11@example.com', '+91 9384412919', 'Sample Logistics', (SELECT id FROM firm_service WHERE slug = 'income-tax'), (SELECT id FROM office_location WHERE slug = 'bengaluru'), 'Need help filing income tax returns and replying to a notice.', '/services/income-tax', 'CONTACTED', 'Not a fit, referred elsewhere.', '2026-07-17 01:33:00', '2026-07-18 06:33:00'),
    ('Suresh Shah', 'suresh.shah12@example.com', '+91 9248532577', 'Sample Tech Pvt Ltd', (SELECT id FROM firm_service WHERE slug = 'nri-taxation'), (SELECT id FROM office_location WHERE slug = 'mumbai'), 'I am an NRI selling a flat in India and need help with TDS and repatriation.', '/contact', 'NEW', NULL, '2026-07-18 05:07:00', '2026-07-19 02:07:00'),
    ('Lakshmi Reddy', 'lakshmi.reddy13@example.com', '+91 9950488739', 'Sample Exports', (SELECT id FROM firm_service WHERE slug = 'bookkeeping'), (SELECT id FROM office_location WHERE slug = 'mumbai'), 'Need monthly bookkeeping on Zoho Books.', '/locations/mumbai', 'NEW', NULL, '2026-07-19 20:12:00', '2026-07-22 16:12:00'),
    ('Nikhil Joshi', 'nikhil.joshi14@example.com', '+91 9369953851', 'Example Motors', (SELECT id FROM firm_service WHERE slug = 'nri-taxation'), (SELECT id FROM office_location WHERE slug = 'hyderabad'), 'I am an NRI selling a flat in India and need help with TDS and repatriation.', '/services/nri-taxation', 'NEW', NULL, '2026-07-21 08:12:00', '2026-07-21 23:12:00'),
    ('Pooja Verma', 'pooja.verma15@example.com', '+91 9415143362', 'Example Textiles', (SELECT id FROM firm_service WHERE slug = 'tax-audit'), (SELECT id FROM office_location WHERE slug = 'bengaluru'), 'Our turnover crossed the tax audit limit this year. Need the audit done before September.', '/contact', 'CLOSED', 'Client comparing quotes.', '2026-07-22 09:46:00', '2026-07-25 02:46:00'),
    ('Aditya Nair', 'aditya.nair16@example.com', '+91 9214257751', 'Demo Foods LLP', (SELECT id FROM firm_service WHERE slug = 'fema-compliance'), (SELECT id FROM office_location WHERE slug = 'delhi-ncr'), 'Foreign investment received; need FC-GPR filing.', '/services/fema-compliance', 'PROPOSAL_SENT', 'Proposal shared by email.', '2026-07-24 03:14:00', '2026-07-25 00:14:00'),
    ('Shreya Pillai', 'shreya.pillai17@example.com', '+91 9679153816', 'Example Traders', (SELECT id FROM firm_service WHERE slug = 'business-valuation'), (SELECT id FROM office_location WHERE slug = 'pune'), 'Need a valuation report for a share issue to investors.', '/', 'NEW', NULL, '2026-07-25 09:43:00', '2026-07-27 08:43:00'),
    ('Manoj Sharma', 'manoj.sharma18@example.com', '+91 9992994062', 'Sample Tech Pvt Ltd', (SELECT id FROM firm_service WHERE slug = 'business-valuation'), (SELECT id FROM office_location WHERE slug = 'chennai'), 'Need a valuation report for a share issue to investors.', '/', 'CONTACTED', 'Not a fit, referred elsewhere.', '2026-07-26 14:03:00', '2026-07-29 05:03:00'),
    ('Deepa Menon', 'deepa.menon19@example.com', '+91 9976198296', 'Demo Constructions', (SELECT id FROM firm_service WHERE slug = 'gst-registration'), (SELECT id FROM office_location WHERE slug = 'pune'), 'Looking for GST registration and monthly return filing for a new business.', '/contact', 'NEW', NULL, '2026-07-28 02:06:00', '2026-07-29 12:06:00'),
    ('Sanjay Kulkarni', 'sanjay.kulkarni20@example.com', '+91 9666585408', NULL, (SELECT id FROM firm_service WHERE slug = 'fema-compliance'), (SELECT id FROM office_location WHERE slug = 'pune'), 'Foreign investment received; need FC-GPR filing.', '/contact', 'CONVERTED', 'Not a fit, referred elsewhere.', '2026-07-29 12:59:00', '2026-07-31 04:59:00'),
    ('Neha Patel', 'neha.patel21@example.com', '+91 9528414718', 'Demo Constructions', (SELECT id FROM firm_service WHERE slug = 'bookkeeping'), (SELECT id FROM office_location WHERE slug = 'delhi-ncr'), 'Need monthly bookkeeping on Zoho Books.', '/', 'PROPOSAL_SENT', 'Not a fit, referred elsewhere.', '2026-07-31 02:33:00', '2026-08-01 10:33:00'),
    ('Ravi Singh', 'ravi.singh22@example.com', '+91 9341266931', 'Sample Exports', (SELECT id FROM firm_service WHERE slug = 'gst-registration'), (SELECT id FROM office_location WHERE slug = 'chennai'), 'Looking for GST registration and monthly return filing for a new business.', '/contact', 'NEW', NULL, '2026-08-01 12:26:00', '2026-08-01 13:26:00'),
    ('Swathi Bose', 'swathi.bose23@example.com', '+91 9176228245', 'Example Traders', (SELECT id FROM firm_service WHERE slug = 'startup-advisory'), (SELECT id FROM office_location WHERE slug = 'delhi-ncr'), 'Early-stage startup looking for DPIIT recognition and an ESOP plan.', '/services/startup-advisory', 'NEW', NULL, '2026-08-02 16:52:00', '2026-08-03 02:52:00'),
    ('Aarav Rao', 'aarav.rao24@example.com', '+91 9652070932', NULL, (SELECT id FROM firm_service WHERE slug = 'company-registration'), (SELECT id FROM office_location WHERE slug = 'chennai'), 'Want to incorporate a private limited company with two directors.', '/locations/chennai', 'CLOSED', 'Proposal shared by email.', '2026-08-04 02:58:00', '2026-08-05 10:58:00'),
    ('Priya Das', 'priya.das25@example.com', '+91 9942478695', 'Test Retail Co', (SELECT id FROM firm_service WHERE slug = 'tds-compliance'), (SELECT id FROM office_location WHERE slug = 'mumbai'), 'Received TDS default notices on TRACES and need them resolved.', '/locations/mumbai', 'CONTACTED', 'Meeting scheduled at the office.', '2026-08-05 10:06:00', '2026-08-07 15:06:00'),
    ('Rahul Iyer', 'rahul.iyer26@example.com', '+91 9601463916', 'Sample Logistics', (SELECT id FROM firm_service WHERE slug = 'fema-compliance'), (SELECT id FROM office_location WHERE slug = 'delhi-ncr'), 'Foreign investment received; need FC-GPR filing.', '/services/fema-compliance', 'NEW', NULL, '2026-08-06 19:39:00', '2026-08-07 09:39:00'),
    ('Sneha Kumar', 'sneha.kumar27@example.com', '+91 9366992705', 'Example Motors', (SELECT id FROM firm_service WHERE slug = 'company-registration'), (SELECT id FROM office_location WHERE slug = 'bengaluru'), 'Want to incorporate a private limited company with two directors.', '/locations/bengaluru', 'CLOSED', 'Proposal shared by email.', '2026-08-08 06:39:00', '2026-08-09 14:39:00'),
    ('Vikram Naidu', 'vikram.naidu28@example.com', '+91 9180943908', 'Example Textiles', (SELECT id FROM firm_service WHERE slug = 'tds-compliance'), (SELECT id FROM office_location WHERE slug = 'pune'), 'Received TDS default notices on TRACES and need them resolved.', '/', 'NEW', NULL, '2026-08-09 14:29:00', '2026-08-10 02:29:00'),
    ('Ananya Gupta', 'ananya.gupta29@example.com', '+91 9909134796', 'Placeholder Pharma', (SELECT id FROM firm_service WHERE slug = 'fema-compliance'), (SELECT id FROM office_location WHERE slug = 'bengaluru'), 'Foreign investment received; need FC-GPR filing.', '/locations/bengaluru', 'CONTACTED', 'Meeting scheduled at the office.', '2026-08-11 05:55:00', '2026-08-11 13:55:00'),
    ('Karthik Shah', 'karthik.shah30@example.com', '+91 9276777762', 'Example Motors', (SELECT id FROM firm_service WHERE slug = 'tax-audit'), (SELECT id FROM office_location WHERE slug = 'hyderabad'), 'Our turnover crossed the tax audit limit this year. Need the audit done before September.', '/locations/hyderabad', 'CONVERTED', 'Meeting scheduled at the office.', '2026-08-12 13:59:00', '2026-08-15 13:59:00'),
    ('Divya Reddy', 'divya.reddy31@example.com', '+91 9810678904', 'Placeholder Pharma', (SELECT id FROM firm_service WHERE slug = 'startup-advisory'), (SELECT id FROM office_location WHERE slug = 'mumbai'), 'Early-stage startup looking for DPIIT recognition and an ESOP plan.', '/', 'NEW', NULL, '2026-08-13 21:54:00', '2026-08-16 19:54:00'),
    ('Arjun Joshi', 'arjun.joshi32@example.com', '+91 9165452690', 'Demo Constructions', (SELECT id FROM firm_service WHERE slug = 'startup-advisory'), (SELECT id FROM office_location WHERE slug = 'chennai'), 'Early-stage startup looking for DPIIT recognition and an ESOP plan.', '/contact', 'NEW', NULL, '2026-08-15 04:49:00', '2026-08-15 12:49:00'),
    ('Meera Verma', 'meera.verma33@example.com', '+91 9645276696', NULL, (SELECT id FROM firm_service WHERE slug = 'gst-registration'), (SELECT id FROM office_location WHERE slug = 'bengaluru'), 'Looking for GST registration and monthly return filing for a new business.', '/contact', 'NEW', NULL, '2026-08-16 15:31:00', '2026-08-18 19:31:00'),
    ('Rohan Nair', 'rohan.nair34@example.com', '+91 9228727266', 'Demo Healthcare', (SELECT id FROM firm_service WHERE slug = 'roc-compliance'), (SELECT id FROM office_location WHERE slug = 'bengaluru'), 'Annual ROC filings for the last two years are pending.', '/services/roc-compliance', 'NEW', NULL, '2026-08-18 00:50:00', '2026-08-19 10:50:00'),
    ('Kavya Pillai', 'kavya.pillai35@example.com', '+91 9319321646', 'Demo Foods LLP', (SELECT id FROM firm_service WHERE slug = 'bookkeeping'), (SELECT id FROM office_location WHERE slug = 'delhi-ncr'), 'Need monthly bookkeeping on Zoho Books.', '/services/bookkeeping', 'PROPOSAL_SENT', 'Meeting scheduled at the office.', '2026-08-19 12:16:00', '2026-08-21 23:16:00'),
    ('Suresh Sharma', 'suresh.sharma36@example.com', '+91 9439492674', 'Sample Exports', (SELECT id FROM firm_service WHERE slug = 'business-valuation'), (SELECT id FROM office_location WHERE slug = 'hyderabad'), 'Need a valuation report for a share issue to investors.', '/', 'NEW', NULL, '2026-08-21 02:15:00', '2026-08-21 12:15:00'),
    ('Lakshmi Menon', 'lakshmi.menon37@example.com', '+91 9677280546', 'Sample Tech Pvt Ltd', (SELECT id FROM firm_service WHERE slug = 'company-registration'), (SELECT id FROM office_location WHERE slug = 'pune'), 'Want to incorporate a private limited company with two directors.', '/contact', 'CONTACTED', 'Client comparing quotes.', '2026-08-22 07:11:00', '2026-08-24 07:11:00'),
    ('Nikhil Kulkarni', 'nikhil.kulkarni38@example.com', '+91 9406002884', 'Example Motors', (SELECT id FROM firm_service WHERE slug = 'income-tax'), (SELECT id FROM office_location WHERE slug = 'mumbai'), 'Need help filing income tax returns and replying to a notice.', '/', 'PROPOSAL_SENT', 'Not a fit, referred elsewhere.', '2026-08-23 21:48:00', '2026-08-24 15:48:00'),
    ('Pooja Patel', 'pooja.patel39@example.com', '+91 9383968123', 'Sample Exports', (SELECT id FROM firm_service WHERE slug = 'gst-registration'), (SELECT id FROM office_location WHERE slug = 'hyderabad'), 'Looking for GST registration and monthly return filing for a new business.', '/contact', 'NEW', NULL, '2026-08-25 03:42:00', '2026-08-26 23:42:00'),
    ('Aditya Singh', 'aditya.singh40@example.com', '+91 9318610946', NULL, (SELECT id FROM firm_service WHERE slug = 'bookkeeping'), (SELECT id FROM office_location WHERE slug = 'delhi-ncr'), 'Need monthly bookkeeping on Zoho Books.', '/', 'CONTACTED', 'Client comparing quotes.', '2026-08-26 17:31:00', '2026-08-27 05:31:00'),
    ('Shreya Bose', 'shreya.bose41@example.com', '+91 9781057736', NULL, (SELECT id FROM firm_service WHERE slug = 'tax-audit'), (SELECT id FROM office_location WHERE slug = 'chennai'), 'Our turnover crossed the tax audit limit this year. Need the audit done before September.', '/contact', 'NEW', NULL, '2026-08-27 18:57:00', '2026-08-29 04:57:00'),
    ('Manoj Rao', 'manoj.rao42@example.com', '+91 9273497327', 'Sample Tech Pvt Ltd', (SELECT id FROM firm_service WHERE slug = 'startup-advisory'), (SELECT id FROM office_location WHERE slug = 'mumbai'), 'Early-stage startup looking for DPIIT recognition and an ESOP plan.', '/contact', 'CONVERTED', 'Called back, sent document checklist.', '2026-08-29 12:12:00', '2026-09-01 10:12:00'),
    ('Deepa Das', 'deepa.das43@example.com', '+91 9138684919', 'Example Traders', (SELECT id FROM firm_service WHERE slug = 'fema-compliance'), (SELECT id FROM office_location WHERE slug = 'chennai'), 'Foreign investment received; need FC-GPR filing.', '/services/fema-compliance', 'NEW', NULL, '2026-08-30 19:56:00', '2026-09-01 18:56:00'),
    ('Sanjay Iyer', 'sanjay.iyer44@example.com', '+91 9954829815', 'Demo Healthcare', (SELECT id FROM firm_service WHERE slug = 'fema-compliance'), (SELECT id FROM office_location WHERE slug = 'hyderabad'), 'Foreign investment received; need FC-GPR filing.', '/', 'PROPOSAL_SENT', 'Proposal shared by email.', '2026-09-01 03:07:00', '2026-09-03 01:07:00'),
    ('Neha Kumar', 'neha.kumar45@example.com', '+91 9937643439', 'Demo Foods LLP', (SELECT id FROM firm_service WHERE slug = 'nri-taxation'), (SELECT id FROM office_location WHERE slug = 'mumbai'), 'I am an NRI selling a flat in India and need help with TDS and repatriation.', '/contact', 'NEW', NULL, '2026-09-02 12:55:00', '2026-09-04 17:55:00'),
    ('Ravi Naidu', 'ravi.naidu46@example.com', '+91 9126614158', 'Example Motors', (SELECT id FROM firm_service WHERE slug = 'income-tax'), (SELECT id FROM office_location WHERE slug = 'delhi-ncr'), 'Need help filing income tax returns and replying to a notice.', '/contact', 'PROPOSAL_SENT', 'Proposal shared by email.', '2026-09-04 01:27:00', '2026-09-04 15:27:00'),
    ('Swathi Gupta', 'swathi.gupta47@example.com', '+91 9510751046', 'Test Retail Co', (SELECT id FROM firm_service WHERE slug = 'fema-compliance'), (SELECT id FROM office_location WHERE slug = 'hyderabad'), 'Foreign investment received; need FC-GPR filing.', '/services/fema-compliance', 'CLOSED', 'Meeting scheduled at the office.', '2026-09-05 07:24:00', '2026-09-06 13:24:00'),
    ('Aarav Shah', 'aarav.shah48@example.com', '+91 9339362341', NULL, (SELECT id FROM firm_service WHERE slug = 'statutory-audit'), (SELECT id FROM office_location WHERE slug = 'delhi-ncr'), 'Need a statutory audit for FY 2025-26. AGM is planned for December.', '/', 'CONTACTED', 'Client comparing quotes.', '2026-09-06 20:09:00', '2026-09-08 08:09:00'),
    ('Priya Reddy', 'priya.reddy49@example.com', '+91 9477040284', 'Sample Tech Pvt Ltd', (SELECT id FROM firm_service WHERE slug = 'bookkeeping'), (SELECT id FROM office_location WHERE slug = 'pune'), 'Need monthly bookkeeping on Zoho Books.', '/services/bookkeeping', 'CONVERTED', 'Called back, sent document checklist.', '2026-09-08 07:45:00', '2026-09-09 06:45:00'),
    ('Rahul Joshi', 'rahul.joshi50@example.com', '+91 9723403479', NULL, (SELECT id FROM firm_service WHERE slug = 'virtual-cfo'), (SELECT id FROM office_location WHERE slug = 'hyderabad'), 'Looking for monthly MIS and investor reporting support.', '/services/virtual-cfo', 'NEW', NULL, '2026-09-09 15:22:00', '2026-09-11 23:22:00'),
    ('Sneha Verma', 'sneha.verma51@example.com', '+91 9750911797', NULL, (SELECT id FROM firm_service WHERE slug = 'nri-taxation'), (SELECT id FROM office_location WHERE slug = 'hyderabad'), 'I am an NRI selling a flat in India and need help with TDS and repatriation.', '/locations/hyderabad', 'CONVERTED', 'Called back, sent document checklist.', '2026-09-10 21:52:00', '2026-09-10 22:52:00'),
    ('Vikram Nair', 'vikram.nair52@example.com', '+91 9658260221', 'Demo Healthcare', (SELECT id FROM firm_service WHERE slug = 'business-valuation'), (SELECT id FROM office_location WHERE slug = 'pune'), 'Need a valuation report for a share issue to investors.', '/services/business-valuation', 'CONTACTED', 'Called back, sent document checklist.', '2026-09-12 09:39:00', '2026-09-14 02:39:00'),
    ('Ananya Pillai', 'ananya.pillai53@example.com', '+91 9812306873', 'Test Retail Co', (SELECT id FROM firm_service WHERE slug = 'fema-compliance'), (SELECT id FROM office_location WHERE slug = 'hyderabad'), 'Foreign investment received; need FC-GPR filing.', '/locations/hyderabad', 'PROPOSAL_SENT', 'Meeting scheduled at the office.', '2026-09-13 22:07:00', '2026-09-15 12:07:00'),
    ('Karthik Sharma', 'karthik.sharma54@example.com', '+91 9695295870', 'Sample Exports', (SELECT id FROM firm_service WHERE slug = 'income-tax'), (SELECT id FROM office_location WHERE slug = 'bengaluru'), 'Need help filing income tax returns and replying to a notice.', '/services/income-tax', 'CONVERTED', 'Proposal shared by email.', '2026-09-15 06:07:00', '2026-09-17 10:07:00'),
    ('Divya Menon', 'divya.menon55@example.com', '+91 9688343096', NULL, (SELECT id FROM firm_service WHERE slug = 'fema-compliance'), (SELECT id FROM office_location WHERE slug = 'hyderabad'), 'Foreign investment received; need FC-GPR filing.', '/services/fema-compliance', 'PROPOSAL_SENT', 'Meeting scheduled at the office.', '2026-09-16 13:13:00', '2026-09-19 01:13:00'),
    ('Arjun Kulkarni', 'arjun.kulkarni56@example.com', '+91 9574364121', 'Demo Healthcare', (SELECT id FROM firm_service WHERE slug = 'tds-compliance'), (SELECT id FROM office_location WHERE slug = 'delhi-ncr'), 'Received TDS default notices on TRACES and need them resolved.', '/', 'CONTACTED', 'Proposal shared by email.', '2026-09-18 03:06:00', '2026-09-19 16:06:00'),
    ('Meera Patel', 'meera.patel57@example.com', '+91 9653462378', 'Demo Healthcare', (SELECT id FROM firm_service WHERE slug = 'bookkeeping'), (SELECT id FROM office_location WHERE slug = 'delhi-ncr'), 'Need monthly bookkeeping on Zoho Books.', '/services/bookkeeping', 'PROPOSAL_SENT', 'Proposal shared by email.', '2026-09-19 06:04:00', '2026-09-20 11:04:00'),
    ('Rohan Singh', 'rohan.singh58@example.com', '+91 9966040317', 'Demo Constructions', (SELECT id FROM firm_service WHERE slug = 'company-registration'), (SELECT id FROM office_location WHERE slug = 'bengaluru'), 'Want to incorporate a private limited company with two directors.', '/', 'NEW', NULL, '2026-09-20 14:03:00', '2026-09-23 01:03:00'),
    ('Kavya Bose', 'kavya.bose59@example.com', '+91 9545002643', 'Placeholder Pharma', (SELECT id FROM firm_service WHERE slug = 'bookkeeping'), (SELECT id FROM office_location WHERE slug = 'pune'), 'Need monthly bookkeeping on Zoho Books.', '/contact', 'CONTACTED', 'Meeting scheduled at the office.', '2026-09-22 05:55:00', '2026-09-22 06:55:00');

UPDATE enquiry
   SET reference_no = CONCAT('ENQ-', YEAR(created_at), '-', LPAD(id, 6, '0'))
 WHERE reference_no IS NULL;


-- ---------------------------------------------------------------------
-- Sample job applications (no resume files)
-- ---------------------------------------------------------------------

INSERT INTO job_application (job_id, full_name, email, phone, qualification, resume_path, cover_note, status, created_at) VALUES
    ((SELECT id FROM job_opening WHERE slug = 'articled-assistant-hyderabad'), 'Vikram Naidu', 'vikram.naidu100@example.com', '+91 9906341966', 'CS Executive', NULL, 'Example: interested in this role and available to join within 30 days.', 'RECEIVED', '2026-09-09 22:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'virtual-cfo-manager-bengaluru'), 'Ananya Gupta', 'ananya.gupta101@example.com', '+91 9963406382', 'BBA', NULL, 'Example: interested in this role and available to join within 30 days.', 'RECEIVED', '2026-09-26 04:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'articled-assistant-hyderabad'), 'Karthik Shah', 'karthik.shah102@example.com', '+91 9367574609', 'BBA', NULL, 'Example: interested in this role and available to join within 30 days.', 'RECEIVED', '2026-09-22 05:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'company-secretary-trainee-mumbai'), 'Divya Reddy', 'divya.reddy103@example.com', '+91 9816806128', 'MBA Finance', NULL, 'Example: interested in this role and available to join within 30 days.', 'REJECTED', '2026-09-21 07:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'virtual-cfo-manager-bengaluru'), 'Arjun Joshi', 'arjun.joshi104@example.com', '+91 9757830420', 'CS Executive', NULL, 'Example: interested in this role and available to join within 30 days.', 'REJECTED', '2026-09-25 19:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'gst-executive-bengaluru'), 'Meera Verma', 'meera.verma105@example.com', '+91 9578796089', 'BBA', NULL, 'Example: interested in this role and available to join within 30 days.', 'SHORTLISTED', '2026-09-13 11:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'senior-audit-associate-chennai'), 'Rohan Nair', 'rohan.nair106@example.com', '+91 9907214179', 'ACA', NULL, 'Example: interested in this role and available to join within 30 days.', 'REJECTED', '2026-09-24 15:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'summer-intern-audit-chennai'), 'Kavya Pillai', 'kavya.pillai107@example.com', '+91 9620332086', 'CA Final', NULL, 'Example: interested in this role and available to join within 30 days.', 'RECEIVED', '2026-09-21 04:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'virtual-cfo-manager-bengaluru'), 'Suresh Sharma', 'suresh.sharma108@example.com', '+91 9183197117', 'ACA', NULL, 'Example: interested in this role and available to join within 30 days.', 'RECEIVED', '2026-09-14 00:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'articled-assistant-pune'), 'Lakshmi Menon', 'lakshmi.menon109@example.com', '+91 9460613550', 'M.Com', NULL, 'Example: interested in this role and available to join within 30 days.', 'RECEIVED', '2026-09-08 20:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'transfer-pricing-associate-delhi-ncr'), 'Nikhil Kulkarni', 'nikhil.kulkarni110@example.com', '+91 9348315126', 'CA Inter', NULL, 'Example: interested in this role and available to join within 30 days.', 'HIRED', '2026-09-11 13:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'transfer-pricing-associate-delhi-ncr'), 'Pooja Patel', 'pooja.patel111@example.com', '+91 9168965204', 'CS Executive', NULL, 'Example: interested in this role and available to join within 30 days.', 'SHORTLISTED', '2026-09-25 13:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'transfer-pricing-associate-delhi-ncr'), 'Aditya Singh', 'aditya.singh112@example.com', '+91 9600282414', 'B.Com', NULL, 'Example: interested in this role and available to join within 30 days.', 'RECEIVED', '2026-09-20 08:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'payroll-executive-hyderabad'), 'Shreya Bose', 'shreya.bose113@example.com', '+91 9518197538', 'B.Com', NULL, 'Example: interested in this role and available to join within 30 days.', 'REJECTED', '2026-09-26 23:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'accounts-executive-bengaluru'), 'Manoj Rao', 'manoj.rao114@example.com', '+91 9508431021', 'B.Com', NULL, 'Example: interested in this role and available to join within 30 days.', 'SHORTLISTED', '2026-09-15 03:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'transfer-pricing-associate-delhi-ncr'), 'Deepa Das', 'deepa.das115@example.com', '+91 9909037759', 'CS Executive', NULL, 'Example: interested in this role and available to join within 30 days.', 'INTERVIEW_SCHEDULED', '2026-09-25 17:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'payroll-executive-hyderabad'), 'Sanjay Iyer', 'sanjay.iyer116@example.com', '+91 9958851904', 'CA Final', NULL, 'Example: interested in this role and available to join within 30 days.', 'SHORTLISTED', '2026-09-11 18:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'transfer-pricing-associate-delhi-ncr'), 'Neha Kumar', 'neha.kumar117@example.com', '+91 9393062061', 'BBA', NULL, 'Example: interested in this role and available to join within 30 days.', 'RECEIVED', '2026-09-19 00:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'summer-intern-audit-chennai'), 'Ravi Naidu', 'ravi.naidu118@example.com', '+91 9460916347', 'CS Executive', NULL, 'Example: interested in this role and available to join within 30 days.', 'HIRED', '2026-09-09 11:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'gst-executive-bengaluru'), 'Swathi Gupta', 'swathi.gupta119@example.com', '+91 9601856351', 'B.Com', NULL, 'Example: interested in this role and available to join within 30 days.', 'SHORTLISTED', '2026-09-27 16:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'summer-intern-audit-chennai'), 'Aarav Shah', 'aarav.shah120@example.com', '+91 9706011277', 'B.Com', NULL, 'Example: interested in this role and available to join within 30 days.', 'RECEIVED', '2026-09-20 16:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'accounts-executive-bengaluru'), 'Priya Reddy', 'priya.reddy121@example.com', '+91 9245706210', 'CA Inter', NULL, 'Example: interested in this role and available to join within 30 days.', 'RECEIVED', '2026-09-13 12:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'articled-assistant-pune'), 'Rahul Joshi', 'rahul.joshi122@example.com', '+91 9507059676', 'CA Final', NULL, 'Example: interested in this role and available to join within 30 days.', 'SHORTLISTED', '2026-09-16 08:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'transfer-pricing-associate-delhi-ncr'), 'Sneha Verma', 'sneha.verma123@example.com', '+91 9462388455', 'ACA', NULL, 'Example: interested in this role and available to join within 30 days.', 'REJECTED', '2026-09-20 09:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'articled-assistant-hyderabad'), 'Vikram Nair', 'vikram.nair124@example.com', '+91 9370874491', 'BBA', NULL, 'Example: interested in this role and available to join within 30 days.', 'RECEIVED', '2026-09-25 10:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'articled-assistant-pune'), 'Ananya Pillai', 'ananya.pillai125@example.com', '+91 9155926488', 'CA Final', NULL, 'Example: interested in this role and available to join within 30 days.', 'OFFERED', '2026-09-05 08:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'summer-intern-audit-chennai'), 'Karthik Sharma', 'karthik.sharma126@example.com', '+91 9938842599', 'B.Com', NULL, 'Example: interested in this role and available to join within 30 days.', 'REJECTED', '2026-09-03 17:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'senior-audit-associate-chennai'), 'Divya Menon', 'divya.menon127@example.com', '+91 9365518424', 'B.Com', NULL, 'Example: interested in this role and available to join within 30 days.', 'INTERVIEW_SCHEDULED', '2026-09-08 22:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'gst-executive-bengaluru'), 'Arjun Kulkarni', 'arjun.kulkarni128@example.com', '+91 9356135680', 'BBA', NULL, 'Example: interested in this role and available to join within 30 days.', 'OFFERED', '2026-09-07 07:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'senior-audit-associate-chennai'), 'Meera Patel', 'meera.patel129@example.com', '+91 9705557720', 'BBA', NULL, 'Example: interested in this role and available to join within 30 days.', 'HIRED', '2026-09-13 08:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'articled-assistant-pune'), 'Rohan Singh', 'rohan.singh130@example.com', '+91 9923450567', 'CA Inter', NULL, 'Example: interested in this role and available to join within 30 days.', 'INTERVIEW_SCHEDULED', '2026-09-28 07:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'virtual-cfo-manager-bengaluru'), 'Kavya Bose', 'kavya.bose131@example.com', '+91 9903079055', 'M.Com', NULL, 'Example: interested in this role and available to join within 30 days.', 'REJECTED', '2026-09-09 09:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'articled-assistant-hyderabad'), 'Suresh Rao', 'suresh.rao132@example.com', '+91 9433975497', 'B.Com', NULL, 'Example: interested in this role and available to join within 30 days.', 'RECEIVED', '2026-09-26 23:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'transfer-pricing-associate-delhi-ncr'), 'Lakshmi Das', 'lakshmi.das133@example.com', '+91 9827296111', 'CS Executive', NULL, 'Example: interested in this role and available to join within 30 days.', 'HIRED', '2026-09-10 21:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'payroll-executive-hyderabad'), 'Nikhil Iyer', 'nikhil.iyer134@example.com', '+91 9181600593', 'CA Final', NULL, 'Example: interested in this role and available to join within 30 days.', 'RECEIVED', '2026-09-15 06:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'payroll-executive-hyderabad'), 'Pooja Kumar', 'pooja.kumar135@example.com', '+91 9834717549', 'M.Com', NULL, 'Example: interested in this role and available to join within 30 days.', 'REJECTED', '2026-09-26 13:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'audit-associate-hyderabad'), 'Aditya Naidu', 'aditya.naidu136@example.com', '+91 9940348305', 'MBA Finance', NULL, 'Example: interested in this role and available to join within 30 days.', 'INTERVIEW_SCHEDULED', '2026-09-20 16:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'articled-assistant-pune'), 'Shreya Gupta', 'shreya.gupta137@example.com', '+91 9810303128', 'M.Com', NULL, 'Example: interested in this role and available to join within 30 days.', 'INTERVIEW_SCHEDULED', '2026-09-16 23:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'transfer-pricing-associate-delhi-ncr'), 'Manoj Shah', 'manoj.shah138@example.com', '+91 9113585139', 'BBA', NULL, 'Example: interested in this role and available to join within 30 days.', 'RECEIVED', '2026-09-20 21:00:00'),
    ((SELECT id FROM job_opening WHERE slug = 'summer-intern-audit-chennai'), 'Deepa Reddy', 'deepa.reddy139@example.com', '+91 9488896443', 'BBA', NULL, 'Example: interested in this role and available to join within 30 days.', 'HIRED', '2026-09-08 22:00:00');


-- ---------------------------------------------------------------------
-- Newsletter subscribers
-- ---------------------------------------------------------------------

INSERT INTO newsletter_subscriber (email, active, subscribed_at, unsubscribed_at) VALUES
    ('arjun.kulkarni200+news@example.com', FALSE, '2026-06-24 01:48:00', '2026-07-14 01:48:00'),
    ('meera.patel201+news@example.com', TRUE, '2026-07-06 05:01:00', NULL),
    ('rohan.singh202+news@example.com', TRUE, '2026-08-09 00:30:00', NULL),
    ('kavya.bose203+news@example.com', TRUE, '2026-07-27 04:13:00', NULL),
    ('suresh.rao204+news@example.com', TRUE, '2026-07-12 16:22:00', NULL),
    ('lakshmi.das205+news@example.com', TRUE, '2026-06-12 17:31:00', NULL),
    ('nikhil.iyer206+news@example.com', TRUE, '2026-07-02 23:51:00', NULL),
    ('pooja.kumar207+news@example.com', TRUE, '2026-08-19 06:48:00', NULL),
    ('aditya.naidu208+news@example.com', TRUE, '2026-07-14 08:58:00', NULL),
    ('shreya.gupta209+news@example.com', FALSE, '2026-09-17 19:05:00', '2026-10-07 19:05:00'),
    ('manoj.shah210+news@example.com', TRUE, '2026-08-02 15:14:00', NULL),
    ('deepa.reddy211+news@example.com', TRUE, '2026-09-11 16:49:00', NULL),
    ('sanjay.joshi212+news@example.com', TRUE, '2026-07-07 04:20:00', NULL),
    ('neha.verma213+news@example.com', TRUE, '2026-09-21 17:25:00', NULL),
    ('ravi.nair214+news@example.com', TRUE, '2026-06-03 01:38:00', NULL),
    ('swathi.pillai215+news@example.com', TRUE, '2026-06-11 16:14:00', NULL),
    ('aarav.sharma216+news@example.com', TRUE, '2026-07-24 00:40:00', NULL),
    ('priya.menon217+news@example.com', TRUE, '2026-09-06 16:12:00', NULL),
    ('rahul.kulkarni218+news@example.com', FALSE, '2026-08-01 06:02:00', '2026-08-21 06:02:00'),
    ('sneha.patel219+news@example.com', TRUE, '2026-08-02 23:17:00', NULL),
    ('vikram.singh220+news@example.com', TRUE, '2026-06-03 11:10:00', NULL),
    ('ananya.bose221+news@example.com', TRUE, '2026-06-29 21:48:00', NULL),
    ('karthik.rao222+news@example.com', TRUE, '2026-07-02 18:27:00', NULL),
    ('divya.das223+news@example.com', TRUE, '2026-08-14 20:35:00', NULL),
    ('arjun.iyer224+news@example.com', TRUE, '2026-08-11 02:07:00', NULL),
    ('meera.kumar225+news@example.com', TRUE, '2026-07-26 02:47:00', NULL),
    ('rohan.naidu226+news@example.com', TRUE, '2026-07-16 23:29:00', NULL),
    ('kavya.gupta227+news@example.com', FALSE, '2026-07-10 16:34:00', '2026-07-30 16:34:00'),
    ('suresh.shah228+news@example.com', TRUE, '2026-06-16 14:34:00', NULL),
    ('lakshmi.reddy229+news@example.com', TRUE, '2026-06-17 02:17:00', NULL),
    ('nikhil.joshi230+news@example.com', TRUE, '2026-08-28 14:19:00', NULL),
    ('pooja.verma231+news@example.com', TRUE, '2026-06-29 00:31:00', NULL),
    ('aditya.nair232+news@example.com', TRUE, '2026-09-02 04:07:00', NULL),
    ('shreya.pillai233+news@example.com', TRUE, '2026-08-08 04:22:00', NULL),
    ('manoj.sharma234+news@example.com', TRUE, '2026-06-13 14:37:00', NULL),
    ('deepa.menon235+news@example.com', TRUE, '2026-06-30 20:19:00', NULL),
    ('sanjay.kulkarni236+news@example.com', FALSE, '2026-07-09 08:28:00', '2026-07-29 08:28:00'),
    ('neha.patel237+news@example.com', TRUE, '2026-08-08 12:19:00', NULL),
    ('ravi.singh238+news@example.com', TRUE, '2026-06-06 09:51:00', NULL),
    ('swathi.bose239+news@example.com', TRUE, '2026-07-08 12:18:00', NULL),
    ('aarav.rao240+news@example.com', TRUE, '2026-09-21 00:45:00', NULL),
    ('priya.das241+news@example.com', TRUE, '2026-09-20 08:25:00', NULL),
    ('rahul.iyer242+news@example.com', TRUE, '2026-07-08 00:01:00', NULL),
    ('sneha.kumar243+news@example.com', TRUE, '2026-07-27 19:37:00', NULL),
    ('vikram.naidu244+news@example.com', TRUE, '2026-06-07 16:37:00', NULL),
    ('ananya.gupta245+news@example.com', FALSE, '2026-06-15 10:13:00', '2026-07-05 10:13:00'),
    ('karthik.shah246+news@example.com', TRUE, '2026-08-02 10:31:00', NULL),
    ('divya.reddy247+news@example.com', TRUE, '2026-08-20 09:49:00', NULL),
    ('arjun.joshi248+news@example.com', TRUE, '2026-06-21 03:12:00', NULL),
    ('meera.verma249+news@example.com', TRUE, '2026-06-11 16:28:00', NULL);
