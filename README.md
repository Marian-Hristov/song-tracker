# song-tracker

Important:
They are predefined production roles and musicians roles. When the user wants to add a new role in either categories,
the user must be vigilant that they are adding the new role in the appropriate category. Our application does not check
if the user adds the role to the appropriate category.

Ex: Let's say that the guitarist role isn't predefined. When the user wants to add it as a new role, they have to check
that they're adding it to the musician category instead of the production category. The application will not check if
the new guitarist role is in the musician category instead of the production category.

### Limitations:

- A song can only be 9999.9 seconds long
- A recording can only my 9999.9 seconds long

### Assumptions

#### Recordings
- A recording's creation time cannot be modified

#### Compilations
- A compilation's creation time cannot be modified
- A compilation's duration cannot be directly modified. It is dependent on the segment that it uses