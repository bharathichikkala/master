class UsersController < ApplicationController

before_action :set_user, only: [:edit, :update, :show, :destroy]
before_action :require_same_user, only: [:edit, :update ]
before_action :require_admin, only: [:destroy]

def new
 @user = User.new
end

def create
  #render plain:params[:user].inspect
@user = User.new(user_params)

 if @user.save

  # UsermailMailer.sendmail(@user).deliver
   MailjobJob.perform_now(@user)
   session[:user_id] =@user.id
   flash[:notice] = "User #{@user.name} was successfully created"
   redirect_to root_path
 else
  render 'new'
 end
end



 def show

 end

 def index
  @users= User.paginate(page: params[:page], per_page: 10)

 end

 def edit

 end


 def update
  if @user.update(user_params)

   flash[:notice] = "user was successfully updated"

   redirect_to user_path(@user)
  else
    render 'edit'
  end
end

def destroy
  @user.destroy
  flash[:notice] = "user was successfully deleted"
  redirect_to users_path
end

private
 def user_params
  params.require(:user).permit(:name ,:email,:phone_number,:password)
 end

 def set_user
  @user = User.find(params[:id])
 end

 def require_same_user

   if current_user != @user

     flash[:danger] = "only created user can edit n destory an user"

     redirect_to root_path

   end

 end
 def require_admin

 if logged_in? and !current_user.admin?

 flash[:danger] = "Only admin users can perform that action"

 redirect_to root_path

end
end
end
