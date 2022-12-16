CREATE TABLE employee (
  emp_id BIGINT AUTO_INCREMENT NOT NULL,
   keycloak_id VARCHAR(255) NOT NULL,
   first_name VARCHAR(255) NOT NULL,
   last_name VARCHAR(255) NOT NULL,
   branch_id BIGINT NULL,
   email VARCHAR(255) NOT NULL,
   emp_details BIGINT NULL,
   dept_no VARCHAR(255) NULL,
   gender VARCHAR(255) NULL,
   `role` VARCHAR(255) NULL,
   profile_image_url VARCHAR(255) NULL,
   last_login_date date NULL,
   last_login_date_display date NULL,
   join_date date NULL,
   is_active BIT(1) NOT NULL,
   is_not_locked BIT(1) NOT NULL,
   CONSTRAINT pk_employee PRIMARY KEY (emp_id)
);

CREATE TABLE employee_details (
  emp_id BIGINT NOT NULL,
   dob date NULL,
   favourite_quotes VARCHAR(255) NULL,
   hobby VARCHAR(255) NULL,
   CONSTRAINT pk_employeedetails PRIMARY KEY (emp_id)
);
CREATE TABLE department (
  dept_no VARCHAR(255) NOT NULL,
   dept_name VARCHAR(255) NULL,
   CONSTRAINT pk_department PRIMARY KEY (dept_no)
);

CREATE TABLE dept_manager (
  emp_id BIGINT NOT NULL,
   dept_no VARCHAR(255) NULL,
   branch_id BIGINT NULL,
   from_date date NOT NULL,
   CONSTRAINT pk_dept_manager PRIMARY KEY (emp_id)
);
CREATE TABLE emp_of_the_month (
  id INT AUTO_INCREMENT NOT NULL,
   emp_id BIGINT NULL,
   date date NULL,
   CONSTRAINT pk_emp_of_the_month PRIMARY KEY (id)
);

CREATE TABLE address (
  id BIGINT AUTO_INCREMENT NOT NULL,
   street VARCHAR(255) NULL,
   city VARCHAR(255) NULL,
   state VARCHAR(255) NULL,
   country VARCHAR(255) NULL,
   postal_code VARCHAR(255) NULL,
   CONSTRAINT pk_address PRIMARY KEY (id)

);

CREATE TABLE branch (
  id BIGINT AUTO_INCREMENT NOT NULL,
   address_id BIGINT NULL,
   CONSTRAINT pk_branch PRIMARY KEY (id)
);


ALTER TABLE branch ADD CONSTRAINT FK_BRANCH_ON_ADDRESS FOREIGN KEY (address_id) REFERENCES address (id);

ALTER TABLE emp_of_the_month ADD CONSTRAINT FK_EMP_OF_THE_MONTH_ON_EMP FOREIGN KEY (emp_id) REFERENCES employee (emp_id);
ALTER TABLE dept_manager ADD CONSTRAINT FK_DEPT_MANAGER_ON_BRANCH FOREIGN KEY (branch_id) REFERENCES branch (id);

ALTER TABLE dept_manager ADD CONSTRAINT FK_DEPT_MANAGER_ON_DEPT_NO FOREIGN KEY (dept_no) REFERENCES department (dept_no);

ALTER TABLE department ADD CONSTRAINT uc_department_dept_no UNIQUE (dept_no);

ALTER TABLE employee_details ADD CONSTRAINT uc_employeedetails_emp UNIQUE (emp_id);

ALTER TABLE employee ADD CONSTRAINT uc_employee_email UNIQUE (email);

ALTER TABLE employee ADD CONSTRAINT uc_employee_emp UNIQUE (emp_id);

ALTER TABLE employee ADD CONSTRAINT uc_employee_emp_details UNIQUE (emp_details);

ALTER TABLE employee ADD CONSTRAINT uc_employee_keycloakid UNIQUE (keycloak_id);

ALTER TABLE employee ADD CONSTRAINT FK_EMPLOYEE_ON_BRANCH FOREIGN KEY (branch_id) REFERENCES branch (id);

ALTER TABLE employee ADD CONSTRAINT FK_EMPLOYEE_ON_DEPT_NO FOREIGN KEY (dept_no) REFERENCES department (dept_no);

ALTER TABLE employee ADD CONSTRAINT FK_EMPLOYEE_ON_EMP_DETAILS FOREIGN KEY (emp_details) REFERENCES employee_details (emp_id);
