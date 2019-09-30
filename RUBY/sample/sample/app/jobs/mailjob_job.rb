class MailjobJob < ApplicationJob
  queue_as :default

  def perform(user)
  UsermailMailer.sendmail(user).deliver_later
  end
end
