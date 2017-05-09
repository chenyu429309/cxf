<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <div>
				<table class="table table-bordered">
					<tr>
						<td><label for="permission_name" class="col-sm-2 control-label">权限名</label>
							<input type="text" class="form-control" id="permission_name"
							placeholder="权限名" /></td>
					</tr>
					<tr>
						<td>
						<label for="permission_urlPatten"
							class="col-sm-2 control-label">链接</label> <input type="text"
							class="form-control" id="permission_urlPatten" placeholder="链接" /></td>
					</tr>
					<tr>
						<td>
						<label for="permission_depth"
							class="col-sm-2 control-label">深度</label> <input type="text"
							class="form-control" id="permission_depth" placeholder="深度" /></td>
					</tr>
					<tr>
						<td>
						<label for="permission_descrition"
							class="col-sm-2 control-label">描述</label> <input type="text"
							class="form-control" id="permission_descrition" placeholder="描述" /></td>
					</tr>
					<tr>
						<td>
								<label for="permissionParent" class="col-sm-2 control-label">上級权限</label>
									<select id="permissionParent" class="form-control">
									</select>
							</td>
					</tr>
					<tr>
						<td><button  class="btn" id="saveNewPermission">
							<span class="glyphicon glyphicon-ok-circle"></span>保存
						</button></td>
					</tr>
				</table>
</div>
<script src="${pageContext.request.contextPath}/assets/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/saveNewRole.js"></script>
