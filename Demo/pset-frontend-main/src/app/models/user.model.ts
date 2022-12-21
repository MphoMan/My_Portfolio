export interface IUser{
    id?: number;
    username?: string;
    email?: string;
    password?: string;
    firstName?: string;
    lastName?: string;
    type?: string;
    confirmPassword?: string;
}

export class User implements IUser{
    constructor(public id?: number,
                public username?: string,
                public email?: string,
                public  password?: string,
                public  firstName?: string,
                public lastName?: string,
                public  type?: string,
                public confirmPassword?: string){}
}
